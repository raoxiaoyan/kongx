package com.kongx.serve.controller;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.common.utils.JWTTokenUtils;
import com.kongx.serve.entity.system.Menu;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.ServerConfig;
import com.kongx.serve.service.system.ServerConfigService;
import com.kongx.serve.service.system.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.kongx.common.core.entity.UserInfo.SUPER_ADMIN;

/**
 * @ClassName AuthorizeEndpoint
 * @Description TODO
 * @Author raoxiaoyan
 * @Date 2019/3/20 15:06
 * @Version 1.0
 **/
@RequestMapping
@RestController
@Slf4j
public class AuthorizeController extends BaseController {

    @Autowired
    private ServerConfigService serverConfigService;


    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/authorize/login.do", method = RequestMethod.POST)
    public ResponseEntity<JsonHeaderWrapper<String>> authorize(HttpServletRequest request, HttpServletResponse response) {
        JsonHeaderWrapper<String> jsonHeaderWrapper = init();
        try {
            UserInfo userInfo = userInfoService.login(request.getParameter("username"), request.getParameter("password"));
            jsonHeaderWrapper.setData(JWTTokenUtils.getToken(userInfo));
            this.log(userInfo, OperationLog.OperationType.OPERATION_LOGIN, OperationLog.OperationTarget.SYSTEM, userInfo);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(500);
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return new ResponseEntity<>(jsonHeaderWrapper, HttpStatus.OK);
    }

    public final static String COOKIE_ACCESS_TOKEN = "E_ACCESS_TOKEN";

    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = getCookieByName(request, COOKIE_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = request.getHeader(COOKIE_ACCESS_TOKEN);
        }
        return accessToken;
    }

    public static String getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name)) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/authorize/getUserInfo.do", method = RequestMethod.GET)
    public ResponseEntity<JsonHeaderWrapper> getUserInfo(HttpServletRequest request) {
        Map<String, Object> results = new HashMap<>(3);
        String token = getAccessToken(request);
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            UserInfo userInfo = JWTTokenUtils.decode(token);
            UserInfo entityUserInfo = this.userInfoService.findById(userInfo.getUserId());
            List list = new ArrayList<>();
            list.add("");
            results.put("roles", list);
            ServerConfig superServerConfig = this.serverConfigService.findByKey(SUPER_ADMIN);
            String[] nameList = superServerConfig.getConfigValue().split(",");
            long cnt = Arrays.stream(nameList).filter((name) -> userInfo.getUserId().equals(name)).count();
            //为超级管理员
            if (cnt > 0) {
                entityUserInfo.setRoleName(SUPER_ADMIN);
            }
            results.put("userInfo", entityUserInfo);
            Set<String> pointCodes = new HashSet<>();
            Optional<List<Menu>> optional = this.userInfoService.findAllMenu(entityUserInfo, null, "point", this.systemProfile(userInfo));
            optional.get().stream().forEach((menu -> pointCodes.add(menu.getCode())));
            results.put("permission", pointCodes);
            request.getSession().setAttribute("USER_INFO", entityUserInfo);
            request.getSession().setAttribute("PERMISSIONS", pointCodes);
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(500);
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return new ResponseEntity<>(jsonHeaderWrapper, HttpStatus.OK);
    }

    @RequestMapping(value = "/authorize/getMenu.do", method = RequestMethod.GET)
    public ResponseEntity<JsonHeaderWrapper> getMenu(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.userInfoService.findAllMenu(userInfo, -1, "menu", this.systemProfile(userInfo)));
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(500);
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return new ResponseEntity<>(jsonHeaderWrapper, HttpStatus.OK);
    }

    @RequestMapping(value = "/authorize/logout.do", method = RequestMethod.GET)
    public ResponseEntity<JsonHeaderWrapper> logout(HttpServletRequest request, HttpServletResponse response) {
        JsonHeaderWrapper jsonHeaderWrapper = init();

        return new ResponseEntity<>(jsonHeaderWrapper, HttpStatus.OK);
    }
}
