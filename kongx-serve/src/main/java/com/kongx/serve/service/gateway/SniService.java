package com.kongx.serve.service.gateway;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Sni;
import com.kongx.serve.feign.SniFeignService;
import com.kongx.serve.service.GatewayService;
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

@Service("sniService")
@Import(FeignClientsConfiguration.class)
@Slf4j
public class SniService extends GatewayService<Sni> {
    @Autowired
    public SniService(Decoder decoder, Encoder encoder, RequestInterceptor requestInterceptor) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder)
                .requestInterceptor(requestInterceptor).target(Target.EmptyTarget.create(SniFeignService.class));
        ENTITY_URI = "/snis";
        ENTITY_URI_ID = "/snis/%s";
        CACHE_KEY = "LISTS";
    }

    @Override
    protected String prefix() {
        return "SNIS";
    }

    @Override
    protected CacheResults<KongEntity<Sni>> loadFromClient(KongCacheKey key) throws URISyntaxException {
        log.info("Loading Services {} from kong client!", key);
        KongEntity<Sni> kongEntity = this.kongFeignService.findAll(uri(key.getSystemProfile(), ENTITY_URI));
        Collections.sort(kongEntity.getData());
        return new CacheResults<>(kongEntity);
    }
}
