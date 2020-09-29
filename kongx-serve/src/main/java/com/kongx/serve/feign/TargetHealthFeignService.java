package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.TargetHealth;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "TargetHealthFeignService")
public interface TargetHealthFeignService extends KongFeignService<TargetHealth> {
}
