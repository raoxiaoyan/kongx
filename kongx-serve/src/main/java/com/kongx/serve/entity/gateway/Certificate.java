package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class Certificate implements Comparable {
    private Timestamp created_at;
    private String cert;
    private String key;
    private String id;
    private List<String> tags = new ArrayList<>();
    private List<String> snis = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Certificate sni = null;
        if (o instanceof Certificate) {
            sni = (Certificate) o;
        }
        return sni.created_at.compareTo(this.created_at);
    }

    public Certificate trim() {
        return this;
    }
}
