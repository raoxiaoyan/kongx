/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway.upstream;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-20 17:10:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Unhealthy {
    private int http_failures;
    private List<Integer> http_statuses;
    private int tcp_failures;
    private int timeouts;
}