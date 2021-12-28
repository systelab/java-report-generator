package com.werfen.report.model;

public class FormReportConfiguration {
    private final String outputFilePath;
    private final ReportHeaderConfiguration headerConfiguration;
    private final ReportFooterConfiguration footerConfiguration;

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public ReportHeaderConfiguration getHeaderConfiguration() {
        return headerConfiguration;
    }

    public ReportFooterConfiguration getFooterConfiguration() {
        return footerConfiguration;
    }

    private FormReportConfiguration(FormReportConfigurationBuilder builder) {
        this.outputFilePath = builder.outputFilePath;
        this.headerConfiguration = builder.headerConfiguration;
        this.footerConfiguration = builder.footerConfiguration;
    }

    public static class FormReportConfigurationBuilder {
        private String outputFilePath;
        private ReportHeaderConfiguration headerConfiguration;
        private ReportFooterConfiguration footerConfiguration;

        public FormReportConfigurationBuilder outputFilePath(String outputFilePath) {
            this.outputFilePath = outputFilePath;
            return this;
        }

        public FormReportConfigurationBuilder headerConfiguration(ReportHeaderConfiguration headerConfiguration) {
            this.headerConfiguration = headerConfiguration;
            return this;
        }

        public  FormReportConfigurationBuilder footerConfiguration(ReportFooterConfiguration footerConfiguration) {
            this.footerConfiguration = footerConfiguration;
            return this;
        }

        public FormReportConfiguration build() {
            return new FormReportConfiguration(this);
        }
    }
}
