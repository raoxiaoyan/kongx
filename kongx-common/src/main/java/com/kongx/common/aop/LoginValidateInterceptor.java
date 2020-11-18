package com.kongx.common.aop;

import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.common.utils.JWTTokenUtils;
import com.kongx.common.utils.Jackson2Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginValidateInterceptor implements HandlerInterceptor {
    static Logger log = LoggerFactory.getLogger(LoginValidateInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginValidateInterceptor preHandle " + request.getRequestURL());
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
        return true;
    }
}
