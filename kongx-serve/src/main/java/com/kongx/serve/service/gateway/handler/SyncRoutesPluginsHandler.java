package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.*;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import org.springframework.stereotype.Component;

@Component("syncRoutesPluginsHandler")
public class SyncRoutesPluginsHandler extends SyncPluginsHandler {


    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        KongEntity<Route> routes = this.routeService.findAllByService(srcClient, service.getId());

        for (Route route : routes.getData()) {
            this.syncRoutePlugins(syncConfig, route, service, srcClient, destClient);
        }

        return null;
    }

    private void syncRoutePlugins(SyncConfig syncConfig, Route route, Service service, SystemProfile srcClient, SystemProfile destClient) throws Exception {
        SyncLog syncLog = this.syncLog(syncConfig, route, service, srcClient, destClient);
        try {
            KongEntity<PluginVO> plugins = this.pluginService.findAllByRoute(srcClient, route.getId());
            Route destRoute = this.routeService.find(destClient, route.getName());
            EntityId entityId = new EntityId();
            if (destRoute != null) {
                entityId.setId(destRoute.getId());
            } else {
                entityId.setId(route.getId());
            }
            for (PluginVO plugin : plugins.getData()) {
                plugin.setRoute(entityId);
                if (destRoute != null) {
                    plugin.setApplyTo(routeService.findAll(destClient).getData(), serviceService.findAll(destClient).getData());
                } else {
                    plugin.setApplyTo(routeService.findAll(srcClient).getData(), serviceService.findAll(srcClient).getData());
                }
                this.syncPlugin(syncConfig, plugin, service, srcClient, destClient);
            }
            syncLog.setComment(String.format("[Route_Plugins]:%s，同步路由插件列表成功", route.getName()));
        } catch (Exception e) {
            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
            syncLog.setComment(String.format("[Route_Plugins]:%s，同步路由插件列表失败,异常信息：%s", route.getName(), e.getMessage()));
            throw new Exception(syncLog.getComment());
        } finally {
            this.syncLogService.add(syncLog);
        }
    }
}
