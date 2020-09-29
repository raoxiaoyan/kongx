package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.*;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("syncServicePluginsHandler")
public class SyncServicePluginsHandler extends SyncPluginsHandler {

    @Resource(name = "syncRoutesHandler")
    private ISyncHandler syncRoutesHandler;

    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        Service destService = findService(destClient, service);
        KongEntity<PluginVO> plugins = pluginService.findAllByService(srcClient, service.getId());
        for (PluginVO plugin : plugins.getData()) {
            EntityId entityId = new EntityId();
            entityId.setId(destService.getId());
            plugin.setService(entityId);
            if (destService != null) {
                plugin.setApplyTo(routeService.findAll(destClient).getData(), serviceService.findAll(destClient).getData());
            } else {
                plugin.setApplyTo(routeService.findAll(srcClient).getData(), serviceService.findAll(srcClient).getData());
            }
            this.syncPlugin(syncConfig, plugin, service, srcClient, destClient);
        }
        return syncRoutesHandler;
    }
}
