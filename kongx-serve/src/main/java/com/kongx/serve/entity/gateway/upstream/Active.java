/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway.upstream;

import lombok.Data;

/**
 * Auto-generated: 2019-11-20 17:10:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Active {

    private ActiveUnhealthy unhealthy;
    private String type;
    private String http_path;
    private int timeout;
    private ActiveHealthy healthy;
    private String https_sni;
    private boolean https_verify_certificate;
    private int concurrency;

}