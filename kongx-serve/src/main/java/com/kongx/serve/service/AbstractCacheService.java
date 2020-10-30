package com.kongx.serve.service;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.service.system.ServerConfigService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCacheService<T> {

    @Autowired
    protected ServerConfigService serverConfigService;

    public URI uri(SystemProfile systemProfile, String path) throws URISyntaxException {
        String url = systemProfile.getUrl();

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return new URI(url.concat(path));
    }

    //本地缓存
    protected LoadingCache<KongCacheKey, CacheResults<T>> SGP_LOCAL_CACHE = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .expireAfterWrite(5, TimeUnit.SECONDS).build(new CacheLoader<KongCacheKey, CacheResults<T>>() {
                @Override
                public CacheResults<T> load(KongCacheKey key) throws Exception {
                    return loadFromClient(key);
                }
            });

    protected KongCacheKey cacheKey(SystemProfile systemProfile, String key) {
        return cacheKey(systemProfile, prefix(), key);
    }

    private KongCacheKey cacheKey(SystemProfile systemProfile, String prefix, String key) {
        KongCacheKey kongCacheKey = new KongCacheKey();
        StringBuilder builder = new StringBuilder();
        builder.append(prefix).append(":").append(key).append(":");
        if (systemProfile != null)
            builder.append(systemProfile.getProfile());
        kongCacheKey.setKey(builder.toString());
        kongCacheKey.setSystemProfile(systemProfile);
        return kongCacheKey;
    }


    protected CacheResults<T> get(SystemProfile systemProfile, String prefix, String key) {
        try {
            return SGP_LOCAL_CACHE.get(cacheKey(systemProfile, prefix, key));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new CacheResults();
    }

    public CacheResults<T> get(SystemProfile systemProfile, String key) {
        return get(systemProfile, prefix(), key);
    }

    protected CacheResults<T> refresh(SystemProfile systemProfile, String key) {
        SGP_LOCAL_CACHE.refresh(cacheKey(systemProfile, key));
        return get(systemProfile, key);
    }

    protected CacheResults<T> loadFromClient(KongCacheKey key) throws URISyntaxException {
        return new CacheResults();
    }

    protected String prefix() {
        return "";
    }

    @Data
    public static class KongCacheKey {
        private String key;

        private SystemProfile systemProfile;

        private UserInfo userInfo;
    }
}
