package com.kongx.serve.service.gateway;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Upstream;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.feign.KongFeignService;
import com.kongx.serve.feign.UpstreamFeignService;
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

@Service("upstreamService")
@Import(FeignClientsConfiguration.class)
@Slf4j
public class UpstreamService extends AbstractCacheService<KongEntity<Upstream>> {
    private static final String UPSTREAM_URI = "/upstreams";
    private static final String UPSTREAM_URI_ID = "/upstreams/%s";

    private static final String CACHE_UPSTREAM_KEY = "LISTS";

    private KongFeignService<Upstream> kongFeignService;

    @Autowired
    public UpstreamService(Decoder decoder, Encoder encoder, RequestInterceptor requestInterceptor) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder)
                .requestInterceptor(requestInterceptor).target(Target.EmptyTarget.create(UpstreamFeignService.class))
        ;
    }

    /**
     * 查询所有upstream
     *
     * @return
     */
    public KongEntity<Upstream> findAll(SystemProfile systemProfile) {
        return this.get(systemProfile, CACHE_UPSTREAM_KEY).getData();
    }

    /**
     * 新增upstream
     *
     * @param upstream
     * @return
     * @throws URISyntaxException
     */
    public Upstream add(SystemProfile systemProfile, Upstream upstream) throws Exception {
        Upstream upstream1 = this.kongFeignService.add(uri(systemProfile, UPSTREAM_URI), upstream);
        refresh(systemProfile, CACHE_UPSTREAM_KEY);
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
    public Upstream update(SystemProfile systemProfile, String id, Upstream upstream) throws Exception {
        Upstream upstream1 = this.kongFeignService.update(uri(systemProfile, String.format(UPSTREAM_URI_ID, id)), upstream);
        refresh(systemProfile, CACHE_UPSTREAM_KEY);
        return upstream1;
    }

    /**
     * 删除upstream
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Upstream> remove(SystemProfile systemProfile, String id) throws Exception {
        this.kongFeignService.remove(uri(systemProfile, String.format(UPSTREAM_URI_ID, id)));
        return refresh(systemProfile, CACHE_UPSTREAM_KEY).getData();
    }

    /**
     * 查询单个upstream的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public Upstream findUpstream(SystemProfile systemProfile, String id) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(UPSTREAM_URI_ID, id)));
    }

    @Override
    protected CacheResults<KongEntity<Upstream>> loadFromClient(KongCacheKey key) throws URISyntaxException {
        log.info("Loading Services {} from kong client!", key);
        KongEntity<Upstream> kongEntity = this.kongFeignService.findAll(uri(key.getSystemProfile(), UPSTREAM_URI));
        Collections.sort(kongEntity.getData());
        return new CacheResults<>(kongEntity);
    }

    @Override
    protected String prefix() {
        return "UPSTREAMS";
    }
}
