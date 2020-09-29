/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Auto-generated: 2019-11-21 9:44:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class TargetHealth implements Comparable {

    private Timestamp created_at;
    private EntityId upstream;
    private String id;
    private String health;
    private String target;
    private int weight;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        TargetHealth upstream = null;
        if (o instanceof TargetHealth) {
            upstream = (TargetHealth) o;
        }
        return upstream.created_at.compareTo(this.created_at);
    }
}