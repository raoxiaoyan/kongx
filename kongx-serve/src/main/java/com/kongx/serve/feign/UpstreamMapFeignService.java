package com.kongx.serve.feign;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(name = "upstreamMapFeignService")
public interface UpstreamMapFeignService extends KongFeignService<Map> {
}
