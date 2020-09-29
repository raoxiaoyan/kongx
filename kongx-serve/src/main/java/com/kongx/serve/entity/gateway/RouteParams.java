package com.kongx.serve.entity.gateway;

import com.kongx.serve.entity.system.SystemProfile;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RouteParams implements Serializable {
    private EntityId service;

    private String policy;//更新策略(只更新当前环境，同步更新到其它环境 )

    private List<String> hosts;

    private List<SystemProfile> profiles;//更新到其它环境中
}
