package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KongEntity<T> {

    private String next;
    private List<T> data = new ArrayList<>();
}
