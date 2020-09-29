package com.kongx.serve.service.system;

import com.kongx.common.utils.Jackson2Helper;
import com.kongx.serve.entity.system.ServerConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnvService {
    @Autowired
    private ServerConfigService serverConfigService;

    public List<Map> findAllEnvs() {
        List<Map> results = new ArrayList<>();
        results.addAll(this.loadEnvs("envs"));
        results.addAll(this.loadEnvs("envs_extension"));
        return results;
    }

    public List<Map> findAllConfigTypes() {
        List<Map> results = new ArrayList<>();
        results.addAll(this.loadTypes("config_type"));
        results.addAll(this.loadTypes("config_type_extension"));
        return results;
    }

    private List<Map> loadTypes(String key) {
        ServerConfig superServerConfig = this.serverConfigService.findByKey(key);
        if (superServerConfig == null) return new ArrayList<>();
        return Jackson2Helper.parsonObject(superServerConfig.getConfigValue(), new TypeReference<List<Map>>() {
        });
    }

    private List<Map> loadEnvs(String key) {
        ServerConfig superServerConfig = this.serverConfigService.findByKey(key);
        if (superServerConfig == null) return new ArrayList<>();
        List<Map> envs = Jackson2Helper.parsonObject(superServerConfig.getConfigValue(), new TypeReference<List<Map>>() {
        });
        List<Map> results = new ArrayList<>();
        envs.forEach((env) -> {
            Map map = new HashMap<>();
            map.put("label", env.get("name"));
            map.put("env", env.get("code"));
            map.put("profileCode", env.get("profileCode"));
            Object deployType = env.get("deployType");
            if (deployType == null) deployType = "";
            map.put("deployType", deployType);
            map.put("groups", profiles(deployType, (ArrayList) env.get("profiles")));
            results.add(map);
        });
        return results;
    }

    private List<Map> profiles(final Object deployType, List<Map> values) {
        List<Map> results = new ArrayList<>();
        values.forEach((profile) -> {
            Map map = new HashMap<>();
            map.put("label", profile.get("code"));
            map.put("value", profile.get("profile"));
            map.put("profile", deployType + "" + profile.get("profile"));
            map.put("profileCode", profile.get("code"));
            results.add(map);
        });
        return results;
    }
}
