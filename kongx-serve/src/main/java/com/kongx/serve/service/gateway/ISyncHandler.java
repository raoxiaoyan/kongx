package com.kongx.serve.service.gateway;

import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;

public interface ISyncHandler {
    ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values)
            throws Exception;
}
