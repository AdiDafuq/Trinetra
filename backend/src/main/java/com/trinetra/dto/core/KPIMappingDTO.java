package com.trinetra.dto.core;

public class KPIMappingDTO {

    private String kpi;
    private String mappedInsight;

    public KPIMappingDTO() {
    }

    public KPIMappingDTO(String kpi, String mappedInsight) {
        this.kpi = kpi;
        this.mappedInsight = mappedInsight;
    }

    public String getKpi() {
        return kpi;
    }

    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    public String getMappedInsight() {
        return mappedInsight;
    }

    public void setMappedInsight(String mappedInsight) {
        this.mappedInsight = mappedInsight;
    }
}