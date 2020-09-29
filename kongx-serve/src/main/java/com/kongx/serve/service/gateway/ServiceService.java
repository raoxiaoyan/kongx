package com.kongx.serve.service.gateway;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.feign.KongFeignService;
import com.kongx.serve.feign.ServiceFeignService;
import com.kongx.serve.service.AbstractService;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;

import java.net.URISyntaxException;
import java.util.Collections;

@Slf4j
@org.springframework.stereotype.Service("serviceService")
@Import(FeignClientsConfiguration.class)
public class ServiceService extends AbstractService<KongEntity<Service>> {
    private static final String CACHE_SERVICES_KEY = "LISTS";
    private static final String SERVICE_URI = "/services";
    private static final String SERVICE_URI_ID = "/services/%s";
    private static final String SERVICE_ROUTE_URI_ID = "/routes/%s/service";
    private static final String SERVICE_PLUGIN_URI_ID = "/plugins/%s/service";

    private KongFeignService<Service> kongFeignService;

    @Autowired
    public ServiceService(Decoder decoder, Encoder encoder) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(Target.EmptyTarget.create(ServiceFeignService.class));
    }

    /**
     * 查询所有SERVICE
     *
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Service> findAll(SystemProfile systemProfile) {
        return this.get(systemProfile, CACHE_SERVICES_KEY).getData();
    }

    /**
     * 新增service
     *
     * @param service
     * @return
     * @throws URISyntaxException
     */
    public Service add(SystemProfile systemProfile, Service service) throws URISyntaxException {
        Service results = this.kongFeignService.add(uri(systemProfile, SERVICE_URI), service);
        refresh(systemProfile, CACHE_SERVICES_KEY);
        return results;
    }


    /**
     * 更新service
     *
     * @param id
     * @param service
     * @return
     * @throws URISyntaxException
     */
    public Service update(SystemProfile systemProfile, String id, Service service) throws URISyntaxException {
        Service service1 = this.kongFeignService.update(uri(systemProfile, String.format(SERVICE_URI_ID, id)), service);
        refresh(systemProfile, CACHE_SERVICES_KEY);
        return service1;
    }

    /**
     * 删除upstream
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Service> remove(SystemProfile systemProfile, String id) throws URISyntaxException {
        this.kongFeignService.remove(uri(systemProfile, String.format(SERVICE_URI_ID, id)));
        return refresh(systemProfile, CACHE_SERVICES_KEY).getData();
    }

    /**
     * 查询单个service的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public Service find(SystemProfile systemProfile, String id) throws URISyntaxException {
        try {
            return this.kongFeignService.findById(uri(systemProfile, String.format(SERVICE_URI_ID, id)));
        } catch (Exception e) {

        }
        return null;
    }

    public Service findByRoute(SystemProfile systemProfile, String routeId) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(SERVICE_ROUTE_URI_ID, routeId)));
    }

    public Service findByPlugin(SystemProfile systemProfile, String pluginId) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(SERVICE_PLUGIN_URI_ID, pluginId)));
    }

    @Override
    protected CacheResults<KongEntity<Service>> loadFromClient(KongCacheKey key) throws URISyntaxException {
        log.info("Loading Services {} from kong client!", key);
        KongEntity<Service> kongEntity = this.kongFeignService.findAll(uri(key.getSystemProfile(), SERVICE_URI));
        Collections.sort(kongEntity.getData());
        CacheResults<KongEntity<Service>> cacheResults = new CacheResults();
        cacheResults.setData(kongEntity);
        return cacheResults;
    }

    @Override
    protected String prefix() {
        return "SERVICES";
    }
}
