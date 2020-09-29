package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Consumer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "consumerFeignService")
public interface ConsumerFeignService extends KongFeignService<Consumer> {
}
