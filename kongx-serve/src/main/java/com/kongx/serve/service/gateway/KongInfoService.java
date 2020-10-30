package com.kongx.serve.service.gateway;

import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.feign.KongInfoFeignService;
import com.kongx.serve.service.AbstractCacheService;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
@Service("kongInfoService")
@Import(FeignClientsConfiguration.class)
public class KongInfoService extends AbstractCacheService {

    private static final String INFO_URI = "/";
    private static final String STATUS_URI = "/status";
    private KongInfoFeignService kongInfoFeignService;

    @Autowired
    public KongInfoService(Decoder decoder, Encoder encoder) {
        kongInfoFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(Target.EmptyTarget.create(KongInfoFeignService.class));
    }

    public Map info(SystemProfile systemProfile) throws URISyntaxException {
        return kongInfoFeignService.info(uri(systemProfile, INFO_URI));
    }

    public Map status(SystemProfile systemProfile) throws URISyntaxException {
        return kongInfoFeignService.status(uri(systemProfile, STATUS_URI));
    }


}
