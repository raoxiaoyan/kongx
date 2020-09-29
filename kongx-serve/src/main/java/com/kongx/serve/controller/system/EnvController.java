package com.kongx.serve.controller.system;

import com.kongx.serve.controller.BaseController;
import com.kongx.serve.service.system.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("/EnvController")
@RequestMapping("/system/envs/")
public class EnvController extends BaseController {

    @Autowired
    private EnvService envService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Map> findAllEnvs() {
        return this.envService.findAllEnvs();
    }

    @RequestMapping(path = "/configTypes", method = RequestMethod.GET)
    public List<Map> findAllConfigType() {
        return this.envService.findAllConfigTypes();
    }

}
