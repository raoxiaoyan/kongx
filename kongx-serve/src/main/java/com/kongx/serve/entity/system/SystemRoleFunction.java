package com.kongx.serve.entity.system;

import lombok.Data;

@Data
public class SystemRoleFunction {
    /**
     * 角色id
     */
    private int roleId;
    /**
     * 菜单id
     */
    private int functionId;

    private String halfChecked = "n";

    public SystemRoleFunction(int roleId, int functionId, String halfChecked) {
        this.roleId = roleId;
        this.functionId = functionId;
        this.halfChecked = halfChecked;
    }

}
