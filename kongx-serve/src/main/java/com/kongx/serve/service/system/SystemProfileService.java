package com.kongx.serve.service.system;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.mapper.SystemProfileMapper;
import com.kongx.serve.service.AbstractCacheService;
import com.kongx.serve.service.gateway.KongInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SystemProfileService")
public class SystemProfileService extends AbstractCacheService {

    @Autowired
    private SystemProfileMapper systemProfileMapper;

    @Autowired
    private EnvService envService;

    @Autowired
    private KongInfoService kongInfoService;

    public Map probing(SystemProfile systemProfile) throws Exception {
        return this.kongInfoService.info(systemProfile);
    }

    protected Cache<String, SystemProfile> KONG_CLIENT_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build();

    public SystemProfile getClientByName(String userName) {
        SystemProfile systemProfile = this.KONG_CLIENT_CACHE.getIfPresent(userName);
        if (systemProfile == null) {
            systemProfile = NULL_CLIENT();
        }
        return systemProfile;
    }

    public void setActiveClient(String username, SystemProfile systemProfile) {
        this.KONG_CLIENT_CACHE.put(username, systemProfile);
    }

    public int addClient(SystemProfile systemProfile) {
        return this.systemProfileMapper.add(systemProfile);
    }

    public int updateClient(SystemProfile systemProfile) {
        return this.systemProfileMapper.update(systemProfile);
    }

    public int removeClient(int id) {
        return this.systemProfileMapper.remove(id);
    }

    private SystemProfile NULL_CLIENT() {
        SystemProfile systemProfile = new SystemProfile();
        systemProfile.setId(-1);
        return systemProfile;
    }

    public List<SystemProfile> findAll() {
        List<SystemProfile> systemProfiles = new ArrayList<>();
        List<SystemProfile> results = this.systemProfileMapper.findAll();
        List<Map> envs = this.envService.findAllEnvs();
        for (SystemProfile result : results) {
            for (Map env : envs) {
                List<Map> profile = (List<Map>) env.get("groups");
                for (Map map : profile) {
                    if (result.getProfile().equals(map.get("label").toString())) {
                        systemProfiles.add(result);
                    }
                }
            }
        }
        return systemProfiles;
    }

    public List<Map<String, Object>> findAllByGroup() {
        List<Map<String, Object>> systemProfiles = new ArrayList<>();
        List<SystemProfile> results = this.systemProfileMapper.findAll();
        List<Map> envs = this.envService.findAllEnvs();
        for (Map env : envs) {
            List<Map> profile = (List<Map>) env.get("groups");
            Map<String, Object> profiles = new HashMap<>();
            List<SystemProfile> systemProfileList = new ArrayList<>();
            for (Map map : profile) {
                for (SystemProfile result : results) {
                    if (result.getProfile().equals(map.get("label").toString())) {
                        systemProfileList.add(result);
                    }
                }
            }
            profiles.put("label", env.get("label"));
            profiles.put("groups", systemProfileList);
            systemProfiles.add(profiles);
        }
        return systemProfiles;
    }

    public SystemProfile findByProfile(String profile) {
        SystemProfile systemProfile = this.systemProfileMapper.findByProfile(profile);
        if (systemProfile == null) {
            systemProfile = new SystemProfile();
//            systemProfile.setProfile(profile);
            systemProfile.setProfileCode(profile);
            systemProfile.setName(profile);
        }
        return systemProfile;
    }
}
