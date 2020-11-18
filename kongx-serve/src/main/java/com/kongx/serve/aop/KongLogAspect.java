package com.kongx.serve.aop;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.utils.JWTTokenUtils;
import com.kongx.common.utils.WebUtil;
import com.kongx.serve.annotation.KongLog;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.system.LogService;
import com.kongx.serve.service.system.SystemProfileService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class KongLogAspect {
    @Autowired
    private LogService logService;

    @Autowired
    private SystemProfileService systemProfileService;

    private SpelExpressionParser parserSpel = new SpelExpressionParser();
    private DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation(com.kongx.serve.annotation.KongLog)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void log(JoinPoint joinPoint) {
        try {
            MethodSignature sign = (MethodSignature) joinPoint.getSignature();
            Method method = sign.getMethod();
            method.getName();
            KongLog kongLog = method.getAnnotation(KongLog.class);
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            String token = request.getHeader("Authorization");

            UserInfo userInfo = JWTTokenUtils.decode(token.replaceAll("Bearer ", ""));
            SystemProfile systemProfile = systemProfileService.getClientByName(userInfo.getUserId());
            OperationLog operationLog = new OperationLog();
            OperationLog.OperationType operationType = kongLog.operation();
            if (kongLog.operation() == OperationLog.OperationType.OPERATION_DEFAULT) {
                operationType = OperationLog.OperationType.mapping(method.getName());
            }
            operationLog.setUserId(userInfo.getUserId());
            operationLog.setProfile(systemProfile.getProfile());
            operationLog.setCreator(userInfo.getName());
            operationLog.setOperation(operationType.getRemark());
            operationLog.setTarget(kongLog.target().getTarget());
            operationLog.setRemark(String.format("%s %s %s", operationLog.getCreator(), operationType.getRemark(), kongLog.target().getTarget()));
            operationLog.setIp(WebUtil.getClientIp(request));
            operationLog.setContent(getValueBykey(kongLog.content(), joinPoint));
            logService.log(systemProfile, operationLog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Object getValueBykey(String spELString, JoinPoint pjp) {
        Expression expression = parserSpel.parseExpression(spELString);
        EvaluationContext context = new StandardEvaluationContext();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context);
    }
}