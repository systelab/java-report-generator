package com.werfen.report.model;

public class ReportFooterConfiguration {
    private final String createdAt;
    private final ReportField additionalField1;
    private final ReportField additionalField2;

    public ReportFooterConfiguration(String createdAt, ReportField additionalField1, ReportField additionalField2) {
        this.createdAt = createdAt;
        this.additionalField1 = additionalField1;
        this.additionalField2 = additionalField2;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public ReportField getAdditionalField1() {
        return additionalField1;
    }
    public ReportField getAdditionalField2() {
        return additionalField2;
    }
}
