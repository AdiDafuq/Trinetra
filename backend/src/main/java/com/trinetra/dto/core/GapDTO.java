package com.trinetra.dto.core;

public class GapDTO {

    private String gap;
    private String impact;

    public GapDTO() {
    }

    public GapDTO(String gap, String impact) {
        this.gap = gap;
        this.impact = impact;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}