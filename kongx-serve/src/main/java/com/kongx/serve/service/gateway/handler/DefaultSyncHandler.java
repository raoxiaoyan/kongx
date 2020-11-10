package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import org.springframework.stereotype.Component;

@Component("defaultSyncHandler")
public class DefaultSyncHandler extends AbstractSyncHandler {
    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        throw new RuntimeException("Default sync Handler!");
    }
}
