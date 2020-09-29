package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.gateway.SyncLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.mapper.SyncConfigMapper;
import com.kongx.serve.service.SyncLogService;
import com.kongx.serve.service.gateway.*;
import com.kongx.serve.service.system.SystemProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractSyncHandler implements ISyncHandler {

    @Autowired
    protected SyncConfigMapper syncConfigMapper;

    @Autowired
    protected ServiceService serviceService;

    @Autowired
    protected UpstreamService upstreamService;

    @Autowired
    protected TargetService targetService;

    @Autowired
    protected SystemProfileService systemProfileService;

    @Autowired
    protected PluginService pluginService;

    @Autowired
    protected RouteService routeService;

    @Autowired
    protected SyncLogService syncLogService;


    protected SyncLog syncLog(SyncConfig syncConfig, Object content, Service service, SystemProfile src, SystemProfile dest) {
        SyncLog syncLog = new SyncLog();
        syncLog.setContent(content);
        syncLog.setService(service.getName());
        syncLog.setSyncNo(syncConfig.getSyncNo());
        syncLog.setSrc_client(src.getUrl());
        syncLog.setDest_client(dest.getUrl());
        return syncLog;
    }

    protected Service findServiceBy(SystemProfile systemProfile, String idOrName) {
        try {
            return this.serviceService.find(systemProfile, idOrName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Service findService(SystemProfile systemProfile, Service srcService) {
        Service destService = this.findServiceBy(systemProfile, srcService.getName());
        if (destService == null) return srcService;
        return destService;
    }

    enum DataType {
        SERVICES("services"),
        ROUTES("routes"),
        PLUGINS("plugins"),
        GLOBAL_PLUGINS("global"),
        UPSTREAMS("upstreams");
        private String type;

        DataType(String type) {
            this.type = type;
        }

        static DataType to(String type) {
            for (DataType value : DataType.values()) {
                if (value.type.equals(type)) {
                    return value;
                }
            }
            return SERVICES;
        }
    }
}
