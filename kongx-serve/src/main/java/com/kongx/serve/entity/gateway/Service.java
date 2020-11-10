/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Auto-generated: 2019-11-20 18:35:55
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Service implements Comparable {

    private String host;
    private Timestamp created_at;
    private int connect_timeout;
    private String id;
    private String protocol;
    private String name;
    private int read_timeout;
    private int port;
    private String path;
    private long updated_at;
    private int retries;
    private int write_timeout;
    /**
     * @since 1.3.x
     */
    private EntityId client_certificate;
    private List<String> tags;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Service upstream = null;
        if (o instanceof Service) {
            upstream = (Service) o;
        }
        return upstream.created_at.compareTo(this.created_at);
    }


    public Service trim() {
        this.protocol = set(this.protocol);
        if (this.client_certificate != null && this.client_certificate.getId() == null) {
            this.client_certificate = null;
        }
        return this;
    }

    private String set(String value) {
        if ("".equals(value)) {
            return null;
        }
        return value;
    }
}