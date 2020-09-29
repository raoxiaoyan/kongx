/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2019-11-20 18:41:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Plugin implements Comparable {

    public static final String AUTH_SERVER_CODE = "auth_server_code";

    private Timestamp created_at;
    private Map config;
    private String id;
    private EntityId service;
    private String name;
    private List<String> protocols;
    private boolean enabled;
    private String run_on;
    private String consumer;
    private EntityId route;
    private String tags;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Plugin upstream = null;
        if (o instanceof Plugin) {
            upstream = (Plugin) o;
        }
        return upstream.created_at.compareTo(this.created_at);
    }

}