package com.kongx.serve.entity.flow;

import lombok.Data;

import java.util.Map;

@Data
public class NodeMeta {
    private Map entity;
    private String id;
    private NodeMeta parent;
    private String name;
    private String prop;
    private boolean ready;
}
