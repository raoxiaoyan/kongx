package com.kongx.serve.entity.system;

import lombok.Data;

import java.util.List;

@Data
public class ProfileRole {
    private String profile;
    private List<SystemRole> roleList;
}
