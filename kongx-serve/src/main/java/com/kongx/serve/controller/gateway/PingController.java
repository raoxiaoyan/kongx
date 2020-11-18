package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.system.SystemProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@RequestMapping
@RestController
public class PingController extends BaseController {
    @Value("${kongx.shell.port:8900}")
    private int port;

    @RequestMapping(value = "/kong/api/ping/shell", method = RequestMethod.GET)
    public JsonHeaderWrapper ping(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            SystemProfile systemProfile = this.systemProfile(userInfo);
            HttpStatus status = HttpStatus.OK;
            if (systemProfile.IS_NULL()) {
                status = HttpStatus.UNAUTHORIZED;
            }
            String url = systemProfile.getUrl();
            String host = url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
            if (!this.isHostConnectable(host)) {
                status = HttpStatus.GATEWAY_TIMEOUT;
            }
            jsonHeaderWrapper.setData(status.value());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    private boolean isHostConnectable(String host) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port), 2000);
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        return true;
    }
}
