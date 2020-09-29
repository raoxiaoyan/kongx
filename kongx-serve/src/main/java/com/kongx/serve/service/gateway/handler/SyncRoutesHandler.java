package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.*;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("syncRoutesHandler")
public class SyncRoutesHandler extends AbstractSyncHandler {


    @Resource(name = "syncRoutesPluginsHandler")
    private ISyncHandler syncRoutesPluginsHandler;

    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        this.syncRoutes(syncConfig, service, srcClient, destClient);
        return syncRoutesPluginsHandler;
    }


    private void syncRoutes(SyncConfig syncConfig, Service service, SystemProfile srcClient, SystemProfile destClient) throws Exception {
        SyncLog syncLog = this.syncLog(syncConfig, service, service, srcClient, destClient);
        try {
            KongEntity<Route> routes = this.routeService.findAllByService(srcClient, service.getId());
            for (Route route : routes.getData()) {
                this.syncRoute(syncConfig, route, service, srcClient, destClient);
            }
            syncLog.setComment(String.format("[Service]:%s，同步路由列表成功", service.getName()));
        } catch (Exception e) {
            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
            syncLog.setComment(String.format("[Service]:%s，同步路由列表失败,异常信息：%s", service.getName(), e.getMessage()));
            throw new Exception(syncLog.getComment());
        } finally {
            this.syncLogService.add(syncLog);
        }
    }

    private void syncRoute(SyncConfig syncConfig, Route route, Service service, SystemProfile srcClient, SystemProfile destClient) throws Exception {
        SyncLog syncLog = this.syncLog(syncConfig, route, service, srcClient, destClient);
        try {
            Service destService = this.findService(destClient, service);
            EntityId entityId = new EntityId();
            entityId.setId(destService.getId());
            route.setService(entityId);
            this.routeService.update(destClient, route.getName(), route);
            syncLog.setComment(String.format("[Route]:%s，同步成功", route.getName()));
        } catch (Exception e) {
            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
            syncLog.setComment(String.format("[Route]:%s，同步失败,异常信息：%s", route.getName(), e.getMessage()));
            throw new Exception(syncLog.getComment());
        } finally {
            this.syncLogService.add(syncLog);
        }
    }
}
