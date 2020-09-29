package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.gateway.SyncEntity;
import com.kongx.serve.entity.gateway.SyncLog;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.SyncLogService;
import com.kongx.serve.service.system.SyncConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/SyncConfigController")
@RequestMapping("/kong/sync/")
public class SyncConfigController extends BaseController {
    @Autowired
    private SyncConfigService syncConfigService;

    @Autowired
    private SyncLogService syncLogService;

    @RequestMapping(value = "/configs", method = RequestMethod.POST)
    public JsonHeaderWrapper add(UserInfo userInfo, @RequestBody SyncConfig syncConfig) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            syncConfig.setCreator(userInfo.getName());
            jsonHeaderWrapper.setData(this.syncConfigService.addSyncConfig(userInfo, syncConfig));
            this.log(userInfo, OperationLog.OperationType.OPERATION_SYNC, OperationLog.OperationTarget.SYNC_SERVICE, syncConfig, remark(userInfo, syncConfig));
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    private String remark(UserInfo userInfo, SyncConfig syncConfig) {
        String remark = String.format(",源环境为：%s", this.systemProfile(userInfo).getProfile());
        StringBuilder names = new StringBuilder();
        for (Service service : syncConfig.getServices()) {
            names.append(service.getName()).append(",");
        }
        StringBuilder profiles = new StringBuilder();
        for (SystemProfile client : syncConfig.getClients()) {
            profiles.append(client.getProfile()).append(",");
        }
        return String.format(remark + ",目标环境列表为：%s服务列表为：%s", profiles, names);
    }

    @RequestMapping(value = "/configs", method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(SyncConfig syncConfig) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            PaginationSupport paginationSupport = this.syncConfigService.findAll(syncConfig);
            jsonHeaderWrapper.setData(paginationSupport);
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/configs/{syncNo}/logs", method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(@PathVariable String syncNo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            List<SyncLog> syncLogList = this.syncLogService.findAllBySyncNo(syncNo);
            jsonHeaderWrapper.setData(syncLogList);
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/configs/logs", method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(SyncLog syncLog) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            List<SyncLog> syncLogList = this.syncLogService.findBySyncNoAndService(syncLog.getSyncNo(), syncLog.getService(), syncLog.getDest_client());
            jsonHeaderWrapper.setData(syncLogList);
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/configs/{syncNo}/config", method = RequestMethod.GET)
    public JsonHeaderWrapper findBySyncNo(@PathVariable String syncNo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            SyncEntity syncConfig = this.syncConfigService.findBySyncConfig(syncNo);
            jsonHeaderWrapper.setData(syncConfig);
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }
}
