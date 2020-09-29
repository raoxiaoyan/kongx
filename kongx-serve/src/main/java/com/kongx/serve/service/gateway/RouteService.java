package com.kongx.serve.service.gateway;

import com.kongx.common.cache.CacheResults;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Route;
import com.kongx.serve.feign.KongFeignService;
import com.kongx.serve.feign.RouteFeignService;
import com.kongx.serve.service.AbstractService;
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
import java.util.Collections;

@Slf4j
@Component("routeService")
@Import(FeignClientsConfiguration.class)
public class RouteService extends AbstractService<KongEntity<Route>> {
    private static final String CACHE_ROUTES_KEY = "LISTS";
    private static final String ROUTE_URI = "/routes";
    private static final String ROUTE_URI_ID = "/routes/%s";
    private static final String ROUTE_PLUGIN_URI_ID = "/plugins/%s/route";
    private static final String ROUTE_SERVICE_URI = "/services/%s/routes";

    private KongFeignService<Route> kongFeignService;

    @Autowired
    public RouteService(Decoder decoder, Encoder encoder) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(Target.EmptyTarget.create(RouteFeignService.class));
    }

    /**
     * 查询所有route
     *
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Route> findAll(SystemProfile systemProfile) {
        return get(systemProfile, CACHE_ROUTES_KEY).getData();
    }

    @Autowired
    private ServiceService serviceService;

    /**
     * 查询所有route
     *
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Route> findAllByService(SystemProfile systemProfile, String serviceId) throws URISyntaxException {
        Service service = this.serviceService.find(systemProfile, serviceId);
        KongEntity<Route> routeKongEntity = new KongEntity<>();
        if (service != null) {
            routeKongEntity = this.kongFeignService.findAll(uri(systemProfile, String.format(ROUTE_SERVICE_URI, serviceId)));
        }
        Collections.sort(routeKongEntity.getData());
        return routeKongEntity;
    }

    /**
     * 新增Route
     *
     * @param route
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Route> add(SystemProfile systemProfile, String serviceId, Route route) throws URISyntaxException {
        this.kongFeignService.add(uri(systemProfile, String.format(ROUTE_SERVICE_URI, serviceId)), route);
        return this.refresh(systemProfile, CACHE_ROUTES_KEY).getData();
    }

    /**
     * 更新Route
     *
     * @param id
     * @param route
     * @return
     * @throws URISyntaxException
     */
    public Route update(SystemProfile systemProfile, String id, Route route) throws URISyntaxException {
        Route results = this.kongFeignService.update(uri(systemProfile, String.format(ROUTE_URI_ID, id)), route);
        this.refresh(systemProfile, CACHE_ROUTES_KEY);
        return results;
    }

    /**
     * 删除Route
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<Route> remove(SystemProfile systemProfile, String id) throws URISyntaxException {
        this.kongFeignService.remove(uri(systemProfile, String.format(ROUTE_URI_ID, id)));
        return this.refresh(systemProfile, CACHE_ROUTES_KEY).getData();
    }

    /**
     * 查询单个route的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public Route find(SystemProfile systemProfile, String id) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(ROUTE_URI_ID, id)));
    }

    /**
     * 通过插件查询单个route的信息
     *
     * @param pluginId
     * @return
     * @throws URISyntaxException
     */
    public Route findByPlugin(SystemProfile systemProfile, String pluginId) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(ROUTE_PLUGIN_URI_ID, pluginId)));
    }


    @Override
    protected CacheResults<KongEntity<Route>> loadFromClient(KongCacheKey kongCacheKey) throws URISyntaxException {
        log.info("Loading Routes {} from kong client!", kongCacheKey);
        KongEntity<Route> kongEntity = this.kongFeignService.findAll(uri(kongCacheKey.getSystemProfile(), ROUTE_URI));
        Collections.sort(kongEntity.getData());
        return new CacheResults<>(kongEntity);
    }

    @Override
    protected String prefix() {
        return "ROUTES";
    }
}
