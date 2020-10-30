package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class Sni implements Comparable {
    private Timestamp created_at;
    private String name;
    private String id;
    private List<String> tags = new ArrayList<>();
    private EntityId certificate;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Sni sni = null;
        if (o instanceof Sni) {
            sni = (Sni) o;
        }
        return sni.created_at.compareTo(this.created_at);
    }

    public Sni trim() {
        return this;
    }
}
