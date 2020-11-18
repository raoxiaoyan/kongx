package com.kongx.serve.config;

import com.kongx.common.aop.LoginValidateInterceptor;
import com.kongx.common.aop.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

@Configurable
public class KongxConfig implements WebMvcConfigurer {
    @Value("${portal.exclude.paths:/shell,/index,/authorize/login.do,/inner/monitor/ping,/health/check,/authorize/getUserInfo.do,/authorize/logout.do," +
            "/index.html,/cdn/**,/css/**,/img/**,/js/**,/svg/**,/util/**,/favicon.ico}")
    private String excludePaths;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ServletWebArgumentResolverAdapter(new UserArgumentResolver()));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginValidateInterceptor()).excludePathPatterns(excludePaths.split(","));
    }
}
