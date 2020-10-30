package com.kongx.serve.service.gateway;

import com.github.pagehelper.util.StringUtil;
import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.gateway.Consumer;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.feign.ConsumerFeignService;
import com.kongx.serve.feign.CredentialsFeignService;
import com.kongx.serve.feign.KongFeignService;
import com.kongx.serve.service.AbstractCacheService;
import feign.Feign;
import feign.RequestInterceptor;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service("ConsumerService")
@Import(FeignClientsConfiguration.class)
@Slf4j
public class ConsumerService extends AbstractCacheService<KongEntity<Consumer>> {
    private static final String CONSUMER_URI = "/consumers";
    private static final String CONSUMER_URI_ID = "/consumers/%s";
    private static final String CREDENTIALS_URI = "/consumers/%s/%s";
    private static final String CREDENTIALS_URI_ID = "/consumers/%s/%s/%s";

    private static final String CACHE_CONSUMERS_KEY = "LISTS";

    private KongFeignService<Consumer> kongFeignService;
    private KongFeignService<Map> credentialsFeignService;

    @Autowired
    public ConsumerService(Decoder decoder, Encoder encoder, RequestInterceptor requestInterceptor) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder)
                .requestInterceptor(requestInterceptor).target(Target.EmptyTarget.create(ConsumerFeignService.class));
        credentialsFeignService = Feign.builder().encoder(encoder).decoder(decoder)
                .requestInterceptor(requestInterceptor).target(Target.EmptyTarget.create(CredentialsFeignService.class));
    }

    /**
     * 查询所有upstream
     *
     * @return
     */
    public KongEntity<Consumer> findAll(SystemProfile systemProfile) {
        return this.get(systemProfile, CACHE_CONSUMERS_KEY).getData();
    }

    public KongEntity<Map> findAllCredentials(SystemProfile systemProfile, String customerId, String credentials) throws URISyntaxException {
        return this.credentialsFeignService.findAll(uri(systemProfile, String.format(CREDENTIALS_URI, customerId, credentials)));
    }

    public Map addCredentials(SystemProfile systemProfile, Map map, String customerId, String credentials) throws URISyntaxException {
        return this.credentialsFeignService.add(uri(systemProfile, String.format(CREDENTIALS_URI, customerId, credentials)), trim(map));
    }

    private Map trim(Map map) {
        Map results = new HashMap();

        map.keySet().stream().forEach((key) -> {
            Object value = map.get(key);
            if (value != null && !StringUtil.isEmpty(value.toString())) {
                results.put(key, value);
            }
        });
        return results;
    }

    public KongEntity<Map> removeCredentials(SystemProfile systemProfile, String customerId, String credentials, String id) throws Exception {
        this.credentialsFeignService.remove(uri(systemProfile, String.format(CREDENTIALS_URI_ID, customerId, credentials, id)));
        return this.findAllCredentials(systemProfile, customerId, credentials);
    }

    /**
     * 新增upstream
     *
     * @param upstream
     * @return
     * @throws URISyntaxException
     */
    public Consumer add(SystemProfile systemProfile, Consumer upstream) throws Exception {
        Consumer upstream1 = this.kongFeignService.add(uri(systemProfile, CONSUMER_URI), upstream);
        refresh(systemProfile, CACHE_CONSUMERS_KEY);
        return upstream1;
    }

    /**
     * 更新upstream
     *
     * @param id
     * @param upstream
     * @return
     * @throws URISyntaxException
     */
    public Consumer update(SystemProfile systemProfile, String id, Consumer upstream) throws Exception {
        Consumer upstream1 = this.kongFeignService.update(uri(systemProfile, String.format(CONSUMER_URI_ID, id)), upstream);
        refresh(systemProfile, CACHE_CONSUMERS_KEY);
        return upstream1;
    }

    /**
     * 删除upstream
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Consumer> remove(SystemProfile systemProfile, String id) throws Exception {
        this.kongFeignService.remove(uri(systemProfile, String.format(CONSUMER_URI_ID, id)));
        return refresh(systemProfile, CACHE_CONSUMERS_KEY).getData();
    }

    /**
     * 查询单个upstream的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public Consumer findConsumer(SystemProfile systemProfile, String id) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(CONSUMER_URI_ID, id)));
    }

    @Override
    protected CacheResults<KongEntity<Consumer>> loadFromClient(KongCacheKey key) throws URISyntaxException {
        log.info("Loading Services {} from kong client!", key);
        KongEntity<Consumer> kongEntity = this.kongFeignService.findAll(uri(key.getSystemProfile(), CONSUMER_URI));
        Collections.sort(kongEntity.getData());
        return new CacheResults<>(kongEntity);
    }

    @Override
    protected String prefix() {
        return "CONSUMERS";
    }
}
