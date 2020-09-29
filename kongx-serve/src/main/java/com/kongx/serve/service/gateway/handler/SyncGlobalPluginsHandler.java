package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("syncGlobalPluginsHandler")
public class SyncGlobalPluginsHandler extends SyncPluginsHandler {
    @Resource(name = "syncServicePluginsHandler")
    private ISyncHandler syncServicePluginsHandler;

    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        //todo 处理全局的插件
        return syncServicePluginsHandler;
    }
}
