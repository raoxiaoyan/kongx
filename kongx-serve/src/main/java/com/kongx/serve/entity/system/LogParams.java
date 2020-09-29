package com.kongx.serve.entity.system;

import lombok.Data;

@Data
public class LogParams {
    private String name;
    private String type;
    private int value;
    private int begin;
    private int end;
    private String label;
    private String keyword;

    public LogParams clone() {
        LogParams logParams = new LogParams();
        logParams.setBegin(begin);
        logParams.setEnd(end);
        logParams.setKeyword(keyword);
        logParams.setLabel(label);
        logParams.setValue(value);
        logParams.setName(name);
        logParams.setType(type);
        return logParams;
    }


}
