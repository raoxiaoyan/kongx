package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.service.gateway.KongInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Map;

@RestController("/KongInfoController")
@RequestMapping("/kong/api/")
public class KongInfoController extends BaseController {
    private static final String INFO_URI = "/info";
    private static final String STATUS_URI = "/status";
    @Autowired
    private KongInfoService kongInfoService;

    @RequestMapping(value = INFO_URI, method = RequestMethod.GET)
    public Map info(UserInfo userInfo) throws URISyntaxException {
        return kongInfoService.info(systemProfile(userInfo));
    }

    @RequestMapping(value = STATUS_URI, method = RequestMethod.GET)
    public Map status(UserInfo userInfo) throws URISyntaxException {
        return kongInfoService.status(systemProfile(userInfo));
    }
}
