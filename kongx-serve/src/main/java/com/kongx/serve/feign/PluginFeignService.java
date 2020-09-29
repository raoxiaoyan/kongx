package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.Plugin;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;
import java.util.Map;

@FeignClient(name = "PluginFeignClient")
public interface PluginFeignService extends KongFeignService<Plugin> {
    @RequestLine("GET")
    Map findPluginName(URI uri);
}
