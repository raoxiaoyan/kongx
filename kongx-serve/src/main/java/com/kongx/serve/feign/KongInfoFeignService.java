package com.kongx.serve.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;
import java.util.Map;

@FeignClient(name = "KongInfoFeignService")
public interface KongInfoFeignService {
    @RequestLine("GET")
    Map info(URI uri);

    @RequestLine("GET")
    Map status(URI uri);
}
