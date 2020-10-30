package com.kongx.serve.entity.gateway;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
/**
 * @since 1.3.x
 */
public class CaCertificate implements Comparable {
    private Timestamp created_at;
    private String cert;
    private String id;
    private List<String> tags = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        CaCertificate sni = null;
        if (o instanceof CaCertificate) {
            sni = (CaCertificate) o;
        }
        return sni.created_at.compareTo(this.created_at);
    }

    public CaCertificate trim() {
        return this;
    }
}
