package com.kongx.serve.entity.system;

import com.kongx.common.utils.Jackson2Helper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class SystemProfile {

    private int id;

    private String profileCode;

    private String name;

    private String profile;

    private String env;

    private String version;

    private String deployType;

    private String ab;//环境缩写

    private String url;

    private String extensions = "[]";//扩展配置

    private String creator;

    private Date create_at = new Date();

    private String remark;

    public boolean IS_NULL() {
        return id == 0 || url == null;
    }

    public System to(String code) {
        List<System> systems = Jackson2Helper.parsonObject(extensions, new TypeReference<List<System>>() {
        });
        for (System system : systems) {
            if (system.code.equals(code)) {
                return system;
            }
        }
        return null;
    }

    @Data
    public static class System {
        private String name;
        private String code;
        private String url;
    }
}
