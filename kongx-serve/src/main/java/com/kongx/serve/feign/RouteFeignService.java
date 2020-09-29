package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Route;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "RouteFeignService")
public interface RouteFeignService extends KongFeignService<Route> {
}
