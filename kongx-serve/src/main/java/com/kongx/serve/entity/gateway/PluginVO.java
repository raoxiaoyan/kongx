/**
 * Copyright 2019 bejson.com
 */
package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2019-11-20 18:41:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class PluginVO extends Plugin {

    private Timestamp created_at;
    private Map config;
    private String id;
    private EntityId service;
    private String name;
    private List<String> protocols;
    private boolean enabled;
    private String run_on;
    private EntityId consumer;
    private EntityId route;
    private String tags;
    private String scope = "global";
    private Map applyObject;

    public Plugin to() {
        Plugin plugin = new Plugin();
        plugin.setCreated_at(created_at);
        plugin.setConfig(config);
        plugin.setId(id);
        plugin.setService(service);
        plugin.setRoute(route);
        plugin.setConsumer(consumer);
        plugin.setEnabled(enabled);
        plugin.setName(name);
        plugin.setProtocols(protocols);
        plugin.setTags(tags);
        plugin.setRun_on(run_on);
        return plugin;
    }

    public String getScope() {
        return scope;
    }

    public void setApplyTo(List<Route> routes, List<Service> services) {
        Map map = new HashMap();
        if (route != null) {
            routes.stream().filter(route1 -> route.getId().equals(route1.getId())).forEach(route1 -> {
                map.put("name", route1.getName());
            });
            this.applyObject = map;
            this.scope = "routes";
            return;
        }
        if (service != null) {
            services.stream().filter(route1 -> service.getId().equals(route1.getId())).forEach(route1 -> {
                map.put("name", route1.getName());
            });
            this.applyObject = map;
            this.scope = "services";
            return;
        }

        map.put("name", "All Entrypoints");
        this.applyObject = map;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        PluginVO upstream = null;
        if (o instanceof PluginVO) {
            upstream = (PluginVO) o;
        }
        return upstream.created_at.compareTo(this.created_at);
    }

}