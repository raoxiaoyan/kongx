package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Upstream;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "upstreamFeignService")
public interface UpstreamFeignService extends KongFeignService<Upstream> {
}
