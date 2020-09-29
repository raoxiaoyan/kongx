package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.*;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.system.ServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SyncPluginsHandler extends AbstractSyncHandler {
    protected List<Route> CACHE_ROUTES = new ArrayList<>();

    protected List<Service> CACHE_SERVICES = new ArrayList<>();

    @Autowired
    private ServerConfigService serverConfigService;

    protected void syncPlugin(SyncConfig syncConfig, PluginVO plugin, Service service, SystemProfile srcClient, SystemProfile destClient) throws Exception {
        SyncLog syncLog = this.syncLog(syncConfig, plugin, service, srcClient, destClient);
        try {
            plugin = filter(destClient, plugin);
            PluginVO existsPlugin = this.exists(destClient, plugin);
            Map applyObject = plugin.getApplyObject();
            if (existsPlugin == null) {
                plugin.setId(null);
                this.pluginService.add(destClient, plugin.to());
            } else {
                plugin.setId(existsPlugin.getId());
                this.pluginService.update(destClient, plugin.getId(), plugin.to());
            }
            syncLog.setComment(String.format("[Plugin]:%s，应用于：[%s],%s,同步成功", plugin.getName(), plugin.getScope(), applyObject.get("name")));
        } catch (Exception e) {
            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
            syncLog.setComment(String.format("[Plugin]:%s，应用于：[%s],%s,同步失败,异常信息：%s", plugin.getName(), plugin.getScope(), plugin.getApplyObject(), e.getMessage()));
            throw new Exception(syncLog.getComment());
        } finally {
            this.syncLogService.add(syncLog);
        }
    }

    private PluginVO exists(SystemProfile destClient, PluginVO plugin) {
        try {
            KongEntity<PluginVO> pluginVOKongEntity = new KongEntity<>();
            if (plugin.getService() != null) {
                pluginVOKongEntity = this.pluginService.findAllByService(destClient, plugin.getService().getId());
            }
            if (plugin.getRoute() != null) {
                pluginVOKongEntity = this.pluginService.findAllByRoute(destClient, plugin.getRoute().getId());
            }
            for (PluginVO datum : pluginVOKongEntity.getData()) {
                if (datum.getName().equals(plugin.getName())) {
                    return datum;
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    private PluginVO filter(SystemProfile dest, PluginVO plugin) {
        if ("xc-auth".equals(plugin.getName())) {
            try {
                URI uri = this.serverConfigService.findUriByCode(dest, Plugin.AUTH_SERVER_CODE);
                plugin.getConfig().put("auth_server", uri.toString());
            } catch (Exception e) {
            }
            return plugin;
        }
        return plugin;
    }
}
