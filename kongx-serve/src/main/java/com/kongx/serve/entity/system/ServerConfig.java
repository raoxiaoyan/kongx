package com.kongx.serve.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class ServerConfig {

    private int id;

    private String configKey;

    private String configValue;

    private String comment;

    private String configType;

    private Date create_at = new Date();

    private String creator;

    private String modifier;

    private Date modify_at = new Date();
}
