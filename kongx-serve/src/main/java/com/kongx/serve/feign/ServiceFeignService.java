package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Service;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ServiceFeignService")
public interface ServiceFeignService extends KongFeignService<Service> {
}
