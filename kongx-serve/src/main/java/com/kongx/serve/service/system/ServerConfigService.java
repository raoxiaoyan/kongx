package com.kongx.serve.service.system;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.system.ServerConfig;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.mapper.ServerConfigMapper;
import com.kongx.serve.service.AbstractCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Service("ServerConfigService")
public class ServerConfigService extends AbstractCacheService<List<ServerConfig>> {
    private static final String SERVERS_CONFIGS_KEY = "LISTS";
    @Autowired
    private ServerConfigMapper serverConfigMapper;

    public List<ServerConfig> findAll() {
        return get(null, SERVERS_CONFIGS_KEY).getData();
    }

    public int addServerConfig(ServerConfig serverConfig) {
        int cnt = this.serverConfigMapper.add(serverConfig);
        this.refresh(null, SERVERS_CONFIGS_KEY);
        return cnt;
    }

    public int updateServerConfig(ServerConfig serverConfig) {
        int cnt = this.serverConfigMapper.update(serverConfig);
        this.refresh(null, SERVERS_CONFIGS_KEY);
        return cnt;
    }

    public ServerConfig findByKey(String key) {
        List<ServerConfig> serverConfigs = this.findAll();
        for (ServerConfig serverConfig : serverConfigs) {
            if (serverConfig.getConfigKey().equals(key)) {
                return serverConfig;
            }
        }
        return this.serverConfigMapper.findByKey(key);
    }

    public URI findUriByCode(SystemProfile systemProfile, String code) throws Exception {
        ServerConfig serverConfig = serverConfigService.findByKey(code);
        SystemProfile.System system = systemProfile.to(serverConfig.getConfigValue().toString());
        if (system == null) throw new Exception("Please set Hot config url");
        String url = system.getUrl();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return new URI(url);
    }


    @Override
    protected CacheResults<List<ServerConfig>> loadFromClient(KongCacheKey kongCacheKey) throws URISyntaxException {
        log.info("Loading SERVER_CONFIGS {} from Local Cache!", kongCacheKey);
        List<ServerConfig> kongEntity = this.serverConfigMapper.findAll();
        return new CacheResults<>(kongEntity);
    }

    @Override
    protected String prefix() {
        return "SERVER_CONFIGS";
    }

}
