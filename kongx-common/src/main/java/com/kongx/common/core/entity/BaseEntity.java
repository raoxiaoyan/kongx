package com.kongx.common.core.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity extends PaginationQC {
    private int id;//主键
    private Date create_at = new Date(); //创建时间
    private String creator;//创建人
    private Date modify_at = new Date();//修改时间
    private String modifier;//修改人
    protected String remark;
}
