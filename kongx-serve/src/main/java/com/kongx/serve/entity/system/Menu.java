package com.kongx.serve.entity.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    private String label;
    private Integer id;
    private Integer parentId;
    private String code;
    private String path;
    private String icon;
    private String component;
    private List<Menu> children = new ArrayList<>();
}
