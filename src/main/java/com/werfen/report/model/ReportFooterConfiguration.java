package com.werfen.report.model;

public class ReportFooterConfiguration {
    private final String createdAt;
    private final ReportField additionalField1;
    private final ReportField additionalField2;

    private ReportFooterConfiguration(ReportFooterConfigurationBuilder builder) {
        this.createdAt = builder.createdAt;
        this.additionalField1 = builder.additionalField1;
        this.additionalField2 = builder.additionalField2;
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

    public static class ReportFooterConfigurationBuilder {
        private String createdAt;
        private ReportField additionalField1;
        private ReportField additionalField2;

        public ReportFooterConfigurationBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReportFooterConfigurationBuilder additionalField1(ReportField additionalField1) {
            this.additionalField1 = additionalField1;
            return this;
        }

        public ReportFooterConfigurationBuilder additionalField2(ReportField additionalField2) {
            this.additionalField2 = additionalField2;
            return this;
        }

        public ReportFooterConfiguration build() {
            return new ReportFooterConfiguration(this);
        }
    }
}
