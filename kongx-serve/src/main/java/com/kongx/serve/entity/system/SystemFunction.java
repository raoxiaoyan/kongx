package com.kongx.serve.entity.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SystemFunction {
    private int id;
    /**
     * 父级菜单id
     */
    private Integer parentId;
    /**
     * 菜单编码
     */
    private String code;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单icon
     */
    private String menuIcon = "";
    /**
     * 是否可用 n-不可用，y-可用
     */
    private String useYn = "y";

    /**
     * 所属系统编码
     */
    private String applicationCode;
    /**
     * 排序
     */
    private Integer sortOrder;
    /**
     * 菜单视图
     */
    private String visitView = "";

    private String menuType = "menu";//菜单类型
    /**
     * 菜单的访问地址
     */
    private String visitPath = "";

    private List<SystemFunction> children = new ArrayList<>();
}
