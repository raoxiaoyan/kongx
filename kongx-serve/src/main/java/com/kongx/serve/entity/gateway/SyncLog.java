package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.util.Date;

@Data
public class SyncLog {

    public static final String LOG_STATUS_FAILURE = "failure";
    public static final String LOG_STATUS_SUCCESS = "success";

    private int id;

    private String service;

    private String syncNo;//同步编号

    private String src_client;

    private String dest_client;

    private String creator;

    private Date create_at = new Date();

    private String status = LOG_STATUS_SUCCESS;

    private Object content;

    private String comment;

}
