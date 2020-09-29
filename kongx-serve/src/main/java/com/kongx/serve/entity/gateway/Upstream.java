/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway;

import com.kongx.serve.entity.gateway.upstream.Healthchecks;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Upstream implements Comparable {

    private Timestamp created_at;
    private String id;
    private String tags;
    private String hash_on;
    private String hash_fallback_header;
    private String hash_on_header;
    private String hash_on_cookie;
    private Healthchecks healthchecks;
    private String hash_on_cookie_path;
    private String name;
    private String hash_fallback;
    private int slots;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Upstream upstream = null;
        if (o instanceof Upstream) {
            upstream = (Upstream) o;
        }
        return upstream.created_at.compareTo(this.created_at);
    }

    public Upstream clear() {
        this.hash_fallback = set(this.hash_fallback);
        this.hash_on = set(this.hash_on);
        this.hash_fallback_header = set(this.hash_fallback_header);
        this.hash_on_header = set(this.hash_on_header);
        this.hash_on_cookie = set(this.hash_on_cookie);
        this.hash_on_cookie_path = set(this.hash_on_cookie_path);
        this.hash_fallback = set(this.hash_fallback);
        if (this.healthchecks != null)
            this.healthchecks.getActive().setHttps_sni(set(this.healthchecks.getActive().getHttps_sni()));
        return this;
    }

    private String set(String value) {
        if ("".equals(value)) {
            return null;
        }
        return value;
    }
}