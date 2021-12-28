package com.werfen.report.model;

public class ReportHeaderConfiguration {
    private final String title;
    private final String logoPath;
    private final ReportField field1;
    private final ReportField field2;
    private final ReportField field3;
    private final ReportField field4;

    public ReportHeaderConfiguration(ReportHeaderConfigurationBuilder builder) {
        this.title = builder.title;
        this.logoPath = builder.logoPath;
        this.field1 = builder.field1;
        this.field2 = builder.field2;
        this.field3 = builder.field3;
        this.field4 = builder.field4;
    }

    public String getTitle() {
        return title;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public ReportField getField1() {
        return field1;
    }

    public ReportField getField2() {
        return field2;
    }

    public ReportField getField3() {
        return field3;
    }

    public ReportField getField4() {
        return field4;
    }

    public static class ReportHeaderConfigurationBuilder {
        private String title;
        private String logoPath;
        private ReportField field1;
        private ReportField field2;
        private ReportField field3;
        private ReportField field4;

        public ReportHeaderConfigurationBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ReportHeaderConfigurationBuilder logoPath(String logoPath) {
            this.logoPath = logoPath;
            return this;
        }

        public ReportHeaderConfigurationBuilder field1(ReportField field1) {
            this.field1 = field1;
            return this;
        }

        public ReportHeaderConfigurationBuilder field2(ReportField field2) {
            this.field2 = field2;
            return this;
        }

        public ReportHeaderConfigurationBuilder field3(ReportField field3) {
            this.field3 = field3;
            return this;
        }

        public ReportHeaderConfigurationBuilder field4(ReportField field4) {
            this.field4 = field4;
            return this;
        }

        public ReportHeaderConfiguration build() {
            return new ReportHeaderConfiguration(this);
        }
    }
}
