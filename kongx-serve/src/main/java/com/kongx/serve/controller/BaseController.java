package com.kongx.serve.controller;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.system.LogService;
import com.kongx.serve.service.system.SystemProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController<T> {

    @Autowired
    private SystemProfileService systemProfileService;

    @Autowired
    protected LogService logService;

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    public JsonHeaderWrapper init() {
        JsonHeaderWrapper jsonHeaderWrapper = new JsonHeaderWrapper();
        return jsonHeaderWrapper;
    }

    protected SystemProfile systemProfile(UserInfo userInfo) {
        return this.systemProfileService.getClientByName(userInfo.getUserId());
    }

    protected void log(UserInfo userInfo, OperationLog.OperationType operationType, OperationLog.OperationTarget operationTarget, Object content) {
        OperationLog operationLog = to(userInfo, operationType, operationTarget, content);
        this.logService.log(systemProfile(userInfo), operationLog);
    }

    private OperationLog to(UserInfo userInfo, OperationLog.OperationType operationType, OperationLog.OperationTarget operationTarget, Object content) {
        OperationLog operationLog = new OperationLog();
        SystemProfile systemProfile = this.systemProfile(userInfo);
        operationLog.setUserId(userInfo.getUserId());
        operationLog.setCreator(userInfo.getName());
        operationLog.setTarget(operationTarget.getType());
        operationLog.setOperation(operationType.getType());
        operationLog.setProfile(systemProfile.getProfile());
        operationLog.setContent(content);
        operationLog.setRemark(String.format("%s %s %s", operationLog.getCreator(), operationType.getRemark(), operationTarget.getTarget()));
        return operationLog;
    }

    protected void log(UserInfo userInfo, OperationLog.OperationType operationType, OperationLog.OperationTarget operationTarget, Object content, String remark) {
        OperationLog operationLog = to(userInfo, operationType, operationTarget, content);
        operationLog.setRemark(String.format("%s %s %s %s", operationLog.getCreator(), operationType.getRemark(), operationTarget.getTarget(), remark));
        this.logService.log(systemProfile(userInfo), operationLog);
    }
}