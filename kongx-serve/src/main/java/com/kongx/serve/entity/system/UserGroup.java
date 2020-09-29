package com.kongx.serve.entity.system;

import com.kongx.common.core.entity.BaseEntity;
import com.kongx.common.core.entity.UserInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserGroup extends BaseEntity {
    private String name;
    private String useYn = "y";
    private List<UserInfo> userList = new ArrayList<>();
    private List<ProfileRole> profileRoles = new ArrayList<>();
}
