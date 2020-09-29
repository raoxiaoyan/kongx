package com.kongx.serve.feign;

import com.kongx.serve.entity.gateway.KongEntity;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

public interface KongFeignService<T> {
    @RequestLine("GET")
    KongEntity<T> findAll(URI uri);

    @RequestLine("GET")
    T findById(URI uri);

    @RequestLine("POST")
    T add(URI uri, @RequestBody T entity);

    @RequestLine("DELETE")
    void remove(URI uri);

    @RequestLine("PUT")
    T update(URI uri, @RequestBody T entity);
}
