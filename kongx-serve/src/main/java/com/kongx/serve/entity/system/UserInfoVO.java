package com.kongx.serve.entity.system;

import com.kongx.common.core.entity.UserInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class UserInfoVO extends UserInfo {
    private List<UserGroup> userGroupList = new ArrayList<>();
}
