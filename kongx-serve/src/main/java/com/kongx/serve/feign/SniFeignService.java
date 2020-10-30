package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Sni;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "sniFeignService")
public interface SniFeignService extends KongFeignService<Sni> {
}
