package com.kongx.serve.service.gateway;

import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.common.core.entity.UserInfo;

public interface ISyncExecutor {
    ISyncExecutor execute(UserInfo userInfo, SyncConfig syncConfig);
}
