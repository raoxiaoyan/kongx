package com.kongx.serve.service.gateway;

import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.feign.TruncateEntityFeignService;
import com.kongx.serve.service.AbstractCacheService;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
@Component("truncateEntityService")
@Import(FeignClientsConfiguration.class)
public class TruncateEntityService extends AbstractCacheService {
    private TruncateEntityFeignService truncateEntityFeignService;

    @Autowired
    public TruncateEntityService(Decoder decoder, Encoder encoder) {
        truncateEntityFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(Target.EmptyTarget.create(TruncateEntityFeignService.class));
    }

    public void remove(SystemProfile systemProfile, String uri) throws URISyntaxException {
        this.truncateEntityFeignService.remove(uri(systemProfile, uri));
    }

    public Map findById(SystemProfile systemProfile, String uri) throws URISyntaxException {
        return this.truncateEntityFeignService.findById(uri(systemProfile, uri));
    }
}
