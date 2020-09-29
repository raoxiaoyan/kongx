package com.kongx.common.aop;

import com.kongx.common.core.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

public class UserArgumentResolver implements WebArgumentResolver {
    static Logger log = LoggerFactory.getLogger(UserArgumentResolver.class);

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if (methodParameter.getParameterType().equals(UserInfo.class)) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            UserInfo dLoginUser = (UserInfo) request.getSession().getAttribute("USER_INFO");
            if (dLoginUser == null) {
                dLoginUser = new UserInfo();
            }
            log.debug("LoginUser={}", dLoginUser);
            return dLoginUser;
        }
        return UNRESOLVED;
    }
}
