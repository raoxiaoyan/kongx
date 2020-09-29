package com.kongx.serve.entity.gateway;

import com.kongx.common.core.entity.PaginationQC;
import com.kongx.serve.entity.system.SystemProfile;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class SyncEntity<T> extends PaginationQC {
    public static final String LOG_STATUS_FAILURE = "failure";
    public static final String LOG_STATUS_RUNNING = "running";
    public static final String LOG_STATUS_SUCCESS = "success";

    public static final int LOG_TYPE_KONG_SERVICES = 0;
    public static final int LOG_TYPE_HOT_CONFIG_PARAMS = 1;

    private int id;

    private SystemProfile src_client;
    private List<SystemProfile> clients;
    private String syncNo = UUID.randomUUID().toString();//同步编号

    private List<T> services;

    private Date create_at = new Date();

    private int logType = LOG_TYPE_KONG_SERVICES;

    private String dataType;

    private String policy;

    private String creator;

    private String comment;

    private String status = LOG_STATUS_RUNNING;
}
