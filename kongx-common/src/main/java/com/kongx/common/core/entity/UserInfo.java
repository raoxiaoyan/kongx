package com.kongx.common.core.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo extends BaseEntity {

    public static final String SUPER_ADMIN = "super_admin";

    public static final String DOMESTIC_CONSUMER = "domestic_consumer";

    public static final String MENUS = "menus";

    public static final String PERMISSIONS = "permission";

    private String userId;//用户Id

    private String name;//用户名

    private String email;//邮箱

    private String password;//密码

    private String mobile;//手机号

    private String roleName = DOMESTIC_CONSUMER;//角色

    private String status = "activate";

    private String creator;

    private Date create_at = new Date();
}
