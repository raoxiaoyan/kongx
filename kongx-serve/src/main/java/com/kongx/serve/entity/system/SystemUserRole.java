package com.kongx.serve.entity.system;

import lombok.Data;

@Data
public class SystemUserRole {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 角色id
     */
    private int roleId;

    public SystemUserRole(String userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}
