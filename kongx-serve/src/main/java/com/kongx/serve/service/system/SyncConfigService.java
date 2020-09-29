package com.kongx.serve.service.system;

import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.core.entity.UserInfo;
import com.github.pagehelper.Page;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.gateway.SyncEntity;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.mapper.SyncConfigMapper;
import com.kongx.serve.service.gateway.ISyncExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class SyncConfigService {
    @Autowired
    private SyncConfigMapper<Service> syncConfigMapper;

    @Autowired
    private SystemProfileService systemProfileService;

    @Autowired
    private ISyncExecutor iSyncExecutor;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public SyncConfig addSyncConfig(UserInfo userInfo, SyncConfig syncConfig) {
        SystemProfile activeSystemProfile = this.systemProfileService.getClientByName(userInfo.getUserId());
        syncConfig.setSrc_client(activeSystemProfile);
        int cnt = this.syncConfigMapper.add(syncConfig);

        if (cnt > 0) {//新增成功
            executorService.submit(() -> iSyncExecutor.execute(userInfo, syncConfig));
        }
        return syncConfig;
    }

    public SyncEntity<Service> findBySyncConfig(String syncNo) {
        return this.syncConfigMapper.findBySyncNo(syncNo);
    }

    public PaginationSupport findAll(SyncEntity syncConfig) {
        PaginationSupport paginationSupport = new PaginationSupport();
        Page syncConfigs = this.syncConfigMapper.findAll(syncConfig.getStart(), syncConfig.getLimit(), syncConfig.getLogType());
        paginationSupport.setItems(syncConfigs);
        paginationSupport.setPageSize(syncConfig.getLimit());
        paginationSupport.setTotalCount(Integer.valueOf(syncConfigs.getTotal() + ""));
        return paginationSupport;
    }
}
