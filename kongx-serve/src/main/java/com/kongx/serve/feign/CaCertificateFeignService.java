package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.CaCertificate;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "caCertificateFeignService")
public interface CaCertificateFeignService extends KongFeignService<CaCertificate> {
}
