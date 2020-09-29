package com.kongx.serve.entity.system;

import com.kongx.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class SystemRole extends BaseEntity {
    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型
     */
    private String roleType;
    /**
     * 排序号
     */
    private Long sortOrder;
    /**
     * 是否可用 n-不可用，y-可用
     */
    private String useYn;
}
