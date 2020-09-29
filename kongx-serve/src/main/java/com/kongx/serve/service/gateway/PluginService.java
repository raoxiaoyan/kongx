package com.kongx.serve.service.gateway;

import com.kongx.serve.entity.gateway.*;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.feign.PluginFeignService;
import com.kongx.serve.feign.PluginVOFeignService;
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
import java.util.List;
import java.util.Map;

@Slf4j
@Component("pluginService")
@Import(FeignClientsConfiguration.class)
public class PluginService extends AbstractService {
    private static final String PLUGIN_URI = "/plugins";
    private static final String PLUGIN_ROUTE_URI = "/routes/%s/plugins";
    private static final String PLUGIN_SERVICE_URI = "/services/%s/plugins";
    private static final String PLUGIN_URI_ID = "/plugins/%s";
    private static final String PLUGIN_URI_SCHEMA_NAME = "/plugins/schema/%s";

    private PluginFeignService kongFeignService;
    private PluginVOFeignService pluginVOFeignService;

    @Autowired
    private RouteService routeService;
    @Autowired
    private ServiceService serviceService;

    @Autowired
    public PluginService(Decoder decoder, Encoder encoder) {
        kongFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(Target.EmptyTarget.create(PluginFeignService.class));
        pluginVOFeignService = Feign.builder().encoder(encoder).decoder(decoder).target(Target.EmptyTarget.create(PluginVOFeignService.class));
    }

    public void rollback(SystemProfile systemProfile, String id, Plugin plugin) throws URISyntaxException {
        //todo rollback
        try {
            Plugin existsPlugin = this.find(systemProfile, id);
        } catch (Exception ex) {
            this.add(systemProfile, plugin);
        }
    }

    public KongEntity<PluginVO> findAll(SystemProfile systemProfile) throws URISyntaxException {
        KongEntity<PluginVO> routeKongEntity = this.pluginVOFeignService.findAll(uri(systemProfile, PLUGIN_URI));
        Collections.sort(routeKongEntity.getData());
        List<Route> routes = routeService.findAll(systemProfile).getData();
        List<Service> services = serviceService.findAll(systemProfile).getData();
        for (PluginVO plugin : routeKongEntity.getData()) {
            plugin.setApplyTo(routes, services);
        }
        return routeKongEntity;
    }

    public KongEntity<PluginVO> findAllPluginByService(SystemProfile systemProfile, String serviceId) throws URISyntaxException {
        KongEntity<PluginVO> routeKongEntity = this.findAll(systemProfile);
        KongEntity<PluginVO> results = new KongEntity<>();
        for (PluginVO vo : routeKongEntity.getData()) {
            if (vo.getScope().equals("services") && vo.getService().getId().equals(serviceId)) {
                results.getData().add(vo);
            }
        }
        List<Route> routeList = this.routeService.findAllByService(systemProfile, serviceId).getData();
        for (PluginVO pluginVO : routeKongEntity.getData()) {
            if (pluginVO.getScope().equals("routes")) {
                for (Route route : routeList) {
                    if (route.getId().equals(pluginVO.getRoute().getId())) {
                        results.getData().add(pluginVO);
                    }
                }
            }
        }

        routeKongEntity.getData().stream().filter(pluginVO -> pluginVO.getScope().equals("global")).forEach(pluginVO -> {
            results.getData().add(pluginVO);
        });
        return results;
    }

    public KongEntity<PluginVO> findAllByRoute(SystemProfile systemProfile, String routeId) throws URISyntaxException {
        KongEntity<PluginVO> routeKongEntity = this.pluginVOFeignService.findAll(uri(systemProfile, String.format(PLUGIN_ROUTE_URI, routeId)));
        Collections.sort(routeKongEntity.getData());
        return routeKongEntity;
    }

    public KongEntity<PluginVO> findAllByService(SystemProfile systemProfile, String serviceId) throws URISyntaxException {
        KongEntity<PluginVO> routeKongEntity = this.pluginVOFeignService.findAll(uri(systemProfile, String.format(PLUGIN_SERVICE_URI, serviceId)));
        Collections.sort(routeKongEntity.getData());
        return routeKongEntity;
    }

    public Plugin add(SystemProfile systemProfile, Plugin plugin) throws URISyntaxException {
        return this.kongFeignService.add(uri(systemProfile, PLUGIN_URI), plugin);
    }

    public Plugin addByRoute(SystemProfile systemProfile, String routeId, Plugin plugin) throws URISyntaxException {
        return this.kongFeignService.add(uri(systemProfile, String.format(PLUGIN_ROUTE_URI, routeId)), plugin);
    }

    public Plugin addByService(SystemProfile systemProfile, String serviceId, Plugin plugin) throws URISyntaxException {
        return this.kongFeignService.add(uri(systemProfile, String.format(PLUGIN_SERVICE_URI, serviceId)), plugin);
    }

    public Plugin update(SystemProfile systemProfile, String id, Plugin plugin) throws URISyntaxException {
        //先删除，再更新
        try {
            this.remove(systemProfile, id);
            return this.kongFeignService.update(uri(systemProfile, String.format(PLUGIN_URI_ID, id)), plugin);
        } catch (Exception e) {
            this.rollback(systemProfile, id, plugin);
        }
        return plugin;
    }

    public void remove(SystemProfile systemProfile, String id) throws URISyntaxException {
        this.kongFeignService.remove(uri(systemProfile, String.format(PLUGIN_URI_ID, id)));
    }

    public Plugin find(SystemProfile systemProfile, String id) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(PLUGIN_URI_ID, id)));
    }

    public Map findPluginSchema(SystemProfile systemProfile, String name) throws URISyntaxException {
        return this.kongFeignService.findPluginName(uri(systemProfile, String.format(PLUGIN_URI_SCHEMA_NAME, name)));
    }

    @Override
    protected String prefix() {
        return "PLUGINS";
    }
}
