package com.kongx.serve.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping
public class HealthCheckController {

    @RequestMapping(value = "/inner/monitor/ping")
    public void ping(HttpServletResponse response) {
        int status = 200;
        File file = new File("./healthcheck.html");
        if (!file.exists()) {
            status = 404;
        }
        response.setStatus(status);
    }

    @RequestMapping(value = "/health/check")
    public void check(HttpServletResponse response) {
        response.setStatus(200);

    }
}
