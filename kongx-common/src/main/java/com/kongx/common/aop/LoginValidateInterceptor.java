package com.kongx.common.aop;

import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.common.utils.JWTTokenUtils;
import com.kongx.common.utils.Jackson2Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class LoginValidateInterceptor implements HandlerInterceptor {
    static Logger log = LoggerFactory.getLogger(LoginValidateInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginValidateInterceptor preHandle " + request.getRequestURL());
        log.debug("start try fetch user info...");
        Object dLoginUser = request.getSession().getAttribute("USER_INFO");
        String token = request.getHeader("Authorization");
        if (token == null || dLoginUser == null || !JWTTokenUtils.verify(token.replaceAll("Bearer ", ""))) {
            // 未登录，转向登录页面！
            JsonHeaderWrapper baseDTO = new JsonHeaderWrapper<>();
            response.setContentType("text/html;charset=UTF-8");
            baseDTO.setErrmsg("Token已失效或用户未登录!");
            PrintWriter writer = response.getWriter();
            Map<String, Object> data = new HashMap<>(2);
            baseDTO.setData(data);
            baseDTO.setStatus(401);
            writer.print(Jackson2Helper.toJsonString(baseDTO));
            return false;
        }
        HandlerMethod method = (HandlerMethod) handler;
        Method method1 = method.getMethod();
        Class[] classes = method1.getParameterTypes();
        for (Class aClass : classes) {
            aClass.getAnnotation(PreAuthorize.class);
        }
        PreAuthorize preAuthorize = method.getMethodAnnotation(PreAuthorize.class);
        if (Optional.ofNullable(preAuthorize).isPresent()) {
            String value = preAuthorize.value();
            if ("".equals(value)) {
                return true;
            }
            Object list = request.getSession().getAttribute("PERMISSIONS");
            if (list != null) {
                Set<String> strings = (Set<String>) list;
                boolean authorize = strings.stream().filter((s) -> value.equalsIgnoreCase(s)).count() > 0;
                if (!authorize) {
                    JsonHeaderWrapper baseDTO = new JsonHeaderWrapper<>();
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("text/html; charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    baseDTO.setErrmsg("拒绝访问");
                    baseDTO.setStatus(403);
                    writer.print(Jackson2Helper.toJsonString(baseDTO));
                }
                return authorize;
            }
        }
        return true;
    }
}
