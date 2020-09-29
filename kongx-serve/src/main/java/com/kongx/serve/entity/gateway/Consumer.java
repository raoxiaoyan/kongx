package com.kongx.serve.entity.gateway;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class Consumer implements Comparable {
    private Timestamp created_at;
    private String username;
    private String custom_id;
    private String id;
    private List<String> tags = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Consumer consumer = null;
        if (o instanceof Consumer) {
            consumer = (Consumer) o;
        }
        return consumer.created_at.compareTo(this.created_at);
    }

    public Consumer trim() {
        this.username = StringUtils.isEmpty(this.username) ? null : this.username;
        this.custom_id = StringUtils.isEmpty(this.custom_id) ? null : this.custom_id;
        return this;
    }
}
