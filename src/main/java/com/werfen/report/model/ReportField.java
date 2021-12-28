package com.werfen.report.model;

public class ReportField {
    private final String name;
    private final String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    private ReportField(ReportFieldBuilder builder) {
        this.name = builder.name;
        this.value = builder.value;
    }

    public static class ReportFieldBuilder {
        private String name;
        private String value;

        public ReportFieldBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ReportFieldBuilder value(String value) {
            this.value = value;
            return this;
        }

        public ReportField build() {
            return new ReportField(this);
        }
    }
}
