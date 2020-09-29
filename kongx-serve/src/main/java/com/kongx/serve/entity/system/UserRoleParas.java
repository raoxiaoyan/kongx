package com.kongx.serve.entity.system;

import com.kongx.common.core.entity.UserInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRoleParas {
    private List<UserInfo> userInfos = new ArrayList<>();
    private List<Integer> systemRoles = new ArrayList<>();
}
