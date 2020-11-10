package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Certificate;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "certificateFeignService")
public interface CertificateFeignService extends KongFeignService<Certificate> {
}
