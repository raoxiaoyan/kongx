package com.kongx.common.cache;

import lombok.Data;

@Data
public class CacheResults<T> {
    private String type;
    private T data;

    public CacheResults() {
    }

    public CacheResults(T data) {
        this.data = data;
    }

}
