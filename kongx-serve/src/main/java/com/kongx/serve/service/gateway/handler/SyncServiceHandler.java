package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.gateway.SyncLog;
import com.kongx.serve.service.gateway.ISyncHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("syncServiceHandler")
public class SyncServiceHandler extends AbstractSyncHandler {


    @Resource(name = "syncServicePluginsHandler")
    private ISyncHandler syncServicePluginsHandler;

    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        this.syncService(syncConfig, service, srcClient, destClient);
        return syncServicePluginsHandler;
    }

    private void syncService(SyncConfig syncConfig, Service service, SystemProfile srcClient, SystemProfile destClient) throws Exception {
        SyncLog syncLog = this.syncLog(syncConfig, service, service, srcClient, destClient);
        try {
            Service destService = this.findServiceBy(destClient, service.getName());
            if (destService == null) {
                this.serviceService.add(destClient, service);
            } else {
                this.serviceService.update(destClient, destService.getId(), service);
            }
            syncLog.setComment(String.format("[Service]:%s，同步成功", service.getName()));
        } catch (Exception e) {
            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
            syncLog.setComment(String.format("[Service]:%s，同步失败,异常信息：%s", service.getName(), e.getMessage()));
            throw new Exception(syncLog.getComment());
        } finally {
            this.syncLogService.add(syncLog);
        }
    }


}
