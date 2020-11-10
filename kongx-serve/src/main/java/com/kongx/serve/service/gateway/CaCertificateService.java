package com.kongx.serve.service.gateway;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.gateway.CaCertificate;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.feign.CaCertificateFeignService;
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

@Service("caCertificateService")
@Import(FeignClientsConfiguration.class)
@Slf4j
public class CaCertificateService extends GatewayService<CaCertificate> {
    @Autowired
    public CaCertificateService(Decoder decoder, Encoder encoder, RequestInterceptor requestInterceptor) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder)
                .requestInterceptor(requestInterceptor).target(Target.EmptyTarget.create(CaCertificateFeignService.class));
        ENTITY_URI = "/ca_certificates";
        ENTITY_URI_ID = "/ca_certificates/%s";
        CACHE_KEY = "LISTS";
    }

    @Override
    protected String prefix() {
        return "CaCertificates";
    }

    @Override
    protected CacheResults<KongEntity<CaCertificate>> loadFromClient(KongCacheKey key) throws URISyntaxException {
        log.info("Loading Services {} from kong client!", key);
        KongEntity<CaCertificate> kongEntity = this.kongFeignService.findAll(uri(key.getSystemProfile(), ENTITY_URI));
        Collections.sort(kongEntity.getData());
        return new CacheResults<>(kongEntity);
    }
}
