package com.kongx.serve.entity.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleMenuParas {
    private SystemRole systemRole;

    private List<Long> ids = new ArrayList<>();

    private List<SystemFunction> items = new ArrayList<>();
    private List<SystemFunction> halfItems = new ArrayList<>();//半选中的菜单
}
