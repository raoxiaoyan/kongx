package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.system.SystemProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ShellController extends BaseController {

    @Value("${kongx.shell.port:8900}")
    private int port;

    @Value("${kongx.shell.dir:/log}")
    private String shellDir;

    @GetMapping("/shell")
    public String shell(UserInfo userInfo) {
        SystemProfile systemProfile = this.systemProfile(userInfo);
        String url = systemProfile.getUrl();
        url = url.substring(0, url.lastIndexOf(":"));

        StringBuilder builder = new StringBuilder("");
        builder.append("redirect:").append(url).append(":").append(this.port).append(shellDir);
        return builder.toString();
    }
}
