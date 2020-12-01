package com.kongx.serve.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;
import java.util.Map;

@FeignClient(name = "truncateEntityFeignService")
public interface TruncateEntityFeignService {

    @RequestLine("GET")
    Map findById(URI uri);

    @RequestLine("DELETE")
    void remove(URI uri);
}
