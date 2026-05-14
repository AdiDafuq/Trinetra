package com.trinetra.dto.core;

public class ScoreDTO {

    private String name;
    private int value;
    private String remark;

    public ScoreDTO() {
    }

    public ScoreDTO(String name, int value, String remark) {
        this.name = name;
        this.value = value;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}