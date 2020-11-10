package com.kongx.serve.service.gateway;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.gateway.Certificate;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.feign.CertificateFeignService;
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

@Service("certificateService")
@Import(FeignClientsConfiguration.class)
@Slf4j
public class CertificateService extends GatewayService<Certificate> {
    @Autowired
    public CertificateService(Decoder decoder, Encoder encoder, RequestInterceptor requestInterceptor) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder)
                .requestInterceptor(requestInterceptor).target(Target.EmptyTarget.create(CertificateFeignService.class));
        ENTITY_URI = "/certificates";
        ENTITY_URI_ID = "/certificates/%s";
        CACHE_KEY = "LISTS";
    }

    @Override
    protected String prefix() {
        return "Certificates";
    }

    @Override
    protected CacheResults<KongEntity<Certificate>> loadFromClient(KongCacheKey key) throws URISyntaxException {
        log.info("Loading Services {} from kong client!", key);
        KongEntity<Certificate> kongEntity = this.kongFeignService.findAll(uri(key.getSystemProfile(), ENTITY_URI));
        Collections.sort(kongEntity.getData());
        return new CacheResults<>(kongEntity);
    }
}
