package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.PluginVO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PluginVOFeignService")
public interface PluginVOFeignService extends KongFeignService<PluginVO>{
}
