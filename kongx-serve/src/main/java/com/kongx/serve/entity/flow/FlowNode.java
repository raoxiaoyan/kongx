package com.kongx.serve.entity.flow;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlowNode {
    private List coordinate = new ArrayList<Integer>();
    private int height;
    private String id;
    private NodeMeta meta;
    private int width;
}
