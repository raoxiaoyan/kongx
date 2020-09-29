package com.kongx.serve.service.gateway;

import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.entity.gateway.Target;
import com.kongx.serve.entity.gateway.TargetHealth;
import com.kongx.serve.feign.KongFeignService;
import com.kongx.serve.feign.TargetFeignService;
import com.kongx.serve.feign.TargetHealthFeignService;
import com.kongx.serve.service.AbstractService;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service("targetService")
@Import(FeignClientsConfiguration.class)
@Slf4j
public class TargetService extends AbstractService {
    private static final String TARGET_URI = "/upstreams/%s/targets";
    private static final String TARGET_URI_HEALTH = "/upstreams/%s/health/";
    private static final String TARGET_URI_ID = "/upstreams/%s/targets/%s";

    private KongFeignService<Target> targetFeignService;
    private KongFeignService<TargetHealth> targetHealthKongFeignService;

    @Autowired
    public TargetService(Decoder decoder, Encoder encoder) {
        targetFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(feign.Target.EmptyTarget.create(TargetFeignService.class));
        targetHealthKongFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(feign.Target.EmptyTarget.create(TargetHealthFeignService.class));
    }

    /**
     * 查询upstream下的所有target
     *
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Target> findAll(SystemProfile systemProfile, String id) throws URISyntaxException {
        return this.targetFeignService.findAll(uri(systemProfile, String.format(TARGET_URI, id)));
    }

    /**
     * 查询upstream下的所有target健康状态
     *
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<TargetHealth> findAllHealth(SystemProfile systemProfile, String id) throws Exception {
        return this.targetHealthKongFeignService.findAll(uri(systemProfile, String.format(TARGET_URI_HEALTH, id)));
    }

    /**
     * 新增upstream
     *
     * @param target
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Target> add(SystemProfile systemProfile, String id, Target target) throws URISyntaxException {
        this.targetFeignService.add(uri(systemProfile, String.format(TARGET_URI, id)), target);
        return findAll(systemProfile, id);
    }

    /**
     * 删除target
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public void remove(SystemProfile systemProfile, String upstreamId, String id) throws URISyntaxException {
        this.targetFeignService.remove(uri(systemProfile, String.format(TARGET_URI_ID, upstreamId, id)));

    }

    public Target findById(SystemProfile systemProfile, String upstreamId, String id) throws URISyntaxException {
        KongEntity<Target> targetKongEntity = this.findAll(systemProfile, upstreamId);
        for (Target entityDatum : targetKongEntity.getData()) {
            if (entityDatum.getId().equals(id)) {
                return entityDatum;
            }
        }
        return null;
    }


    @Override
    protected String prefix() {
        return "TARGETS";
    }
}
