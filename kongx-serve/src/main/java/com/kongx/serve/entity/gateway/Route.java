/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2019-11-20 18:37:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Route implements Comparable {

    private String id;
    private String tags;
    private List<String> paths = new ArrayList<>();
    private String destinations;
    private List<String> protocols;
    private Timestamp created_at;
    private String snis;
    private List<String> hosts = new ArrayList<>();
    private String name;
    private boolean preserve_host;
    private int regex_priority;
    private boolean strip_path;
    private String sources;
    private Timestamp updated_at;
    private int https_redirect_status_code = 426;
    private EntityId service;
    private List<String> methods = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Route upstream = null;
        if (o instanceof Route) {
            upstream = (Route) o;
        }
        return upstream.created_at.compareTo(this.created_at);
    }

    public Route clear() {
        this.snis = set(this.snis);
        this.protocols = set(this.protocols);
        this.paths = set(this.paths);
        this.hosts = set(this.hosts);
        this.methods = set(this.methods);
        return this;
    }

    private String set(String value) {
        if ("".equals(value)) {
            return null;
        }
        return value;
    }

    private List<String> set(List<String> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }
}