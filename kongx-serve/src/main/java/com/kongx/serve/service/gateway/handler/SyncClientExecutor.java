package com.kongx.serve.service.gateway.handler;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.mapper.SyncConfigMapper;
import com.kongx.serve.service.gateway.ISyncExecutor;
import com.kongx.serve.service.gateway.ISyncHandler;
import com.kongx.serve.service.system.SystemProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("syncClientExecutor")
public class SyncClientExecutor implements ISyncExecutor {
    @Resource(name = "syncUpstreamHandler")
    private ISyncHandler syncUpstreamHandler;

    @Resource(name = "syncRoutesHandler")
    private ISyncHandler syncRoutesHandler;

    @Resource(name = "syncServicePluginsHandler")
    private ISyncHandler syncServicePluginsHandler;

    @Resource(name = "syncGlobalPluginsHandler")
    private ISyncHandler syncGlobalPluginsHandler;

    @Autowired
    private SystemProfileService systemProfileService;

    @Autowired
    private SyncConfigMapper<SyncConfig> syncConfigMapper;

    @Override
    public ISyncExecutor execute(UserInfo userInfo, SyncConfig syncConfig) {
        SystemProfile srcClient = this.systemProfileService.getClientByName(userInfo.getUserId());
        try {
            for (SystemProfile destClient : syncConfig.getClients()) {
                for (Service service : syncConfig.getServices()) {
                    this.doHandler(srcClient, destClient, service, syncConfig);
                }
            }
            syncConfig.setStatus(SyncConfig.LOG_STATUS_SUCCESS);
            syncConfig.setComment("同步成功");
        } catch (Exception e) {
            log.error("同步失败,{}:{}", syncConfig, e.getMessage());
            syncConfig.setStatus(SyncConfig.LOG_STATUS_FAILURE);
            syncConfig.setComment(e.getMessage());
        } finally {
            this.syncConfigMapper.update(syncConfig);
        }
        return null;
    }

    private void doHandler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig) throws Exception {
        log.info("Start Sync srcClient:{},destClient:{},service:{}/{},syncConfig:{}/{}", srcClient.getProfile(), destClient.getProfile(), service.getName(), service.getHost()
                , syncConfig.getDataType(), syncConfig.getPolicy());
        AbstractSyncHandler.DataType dataType = AbstractSyncHandler.DataType.to(syncConfig.getDataType());
        switch (dataType) {
            case GLOBAL_PLUGINS:
                //1. 同步全局插件
                syncGlobalPluginsHandler.handler(srcClient, destClient, service, syncConfig)
                        //2.同步服务插件
                        .handler(srcClient, destClient, service, syncConfig)
                        //3. 同步路由信息
                        .handler(srcClient, destClient, service, syncConfig)
                        //4. 同步路由插件
                        .handler(srcClient, destClient, service, syncConfig);
                break;
            case ROUTES:
                //1. 同步路由信息
                syncRoutesHandler.handler(srcClient, destClient, service, syncConfig)
                        //2. 同步路由插件
                        .handler(srcClient, destClient, service, syncConfig);
                break;
            case PLUGINS:
                //1.同步服务插件
                syncServicePluginsHandler.handler(srcClient, destClient, service, syncConfig)
                        //2. 同步路由信息
                        .handler(srcClient, destClient, service, syncConfig)
                        //3. 同步路由插件
                        .handler(srcClient, destClient, service, syncConfig);
                break;
            case SERVICES:
                //1. 同步上游代理
                syncUpstreamHandler.handler(srcClient, destClient, service, syncConfig)
                        //2. 同步代理列表
                        .handler(srcClient, destClient, service, syncConfig)
                        //3. 同步服务信息
                        .handler(srcClient, destClient, service, syncConfig)
                        //4. 同步服务插件
                        .handler(srcClient, destClient, service, syncConfig)
                        //5. 同步路由信息
                        .handler(srcClient, destClient, service, syncConfig)
                        //6. 同步路由插件
                        .handler(srcClient, destClient, service, syncConfig);
                break;
            case UPSTREAMS:
                //1.同步代理信息
                syncUpstreamHandler.handler(srcClient, destClient, service, syncConfig)
                        //2.同步代理列表
                        .handler(srcClient, destClient, service, syncConfig);
                break;
            default:
                break;
        }
    }
}
