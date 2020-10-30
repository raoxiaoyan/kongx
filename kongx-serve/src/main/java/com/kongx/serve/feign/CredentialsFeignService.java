package com.kongx.serve.feign;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(name = "credentialsFeignService")
public interface CredentialsFeignService extends KongFeignService<Map> {
}
