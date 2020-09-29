package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Target;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "targetFeignService")
public interface TargetFeignService extends KongFeignService<Target> {
}
