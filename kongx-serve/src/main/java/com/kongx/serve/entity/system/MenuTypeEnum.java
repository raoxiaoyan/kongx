package com.kongx.serve.entity.system;

public enum MenuTypeEnum {
    APPLICATION("application", "系统", "A"),
    FUNCTION("function", "菜单", "F"),
    POINT("point", "按钮", "P");
    private String code;
    private String name;

    private String ab;

    MenuTypeEnum(String code, String name, String ab) {
        this.code = code;
        this.name = name;
        this.ab = ab;

    }

    public static MenuTypeEnum codeOf(String code) {
        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.code.equals(code)) {
                return menuTypeEnum;
            }
        }
        return null;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
