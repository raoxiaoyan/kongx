package com.kongx.serve.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KongRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
    }
}
