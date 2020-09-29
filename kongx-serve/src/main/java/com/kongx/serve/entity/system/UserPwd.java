package com.kongx.serve.entity.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPwd implements Serializable {
    private String userId;
    private String oldPassword;
    private String newPassword;
}
