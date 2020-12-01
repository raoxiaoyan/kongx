package com.kongx.serve.entity.flow;

import com.kongx.common.core.entity.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ServicePipeline extends BaseEntity {
    private String name;
    private String img = "/img/plugins/kong.svg";
    private List linkList = new ArrayList<Map>();
    private List nodeList = new ArrayList<FlowNode>();
    private List origin = new ArrayList<Integer>();
    private String profile;
}
