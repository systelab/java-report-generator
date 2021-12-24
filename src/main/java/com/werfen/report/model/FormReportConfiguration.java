package com.werfen.report.model;

public class FormReportConfiguration {
    private final String outputFilePath;
    private final ReportHeaderConfiguration headerConfiguration;
    private final ReportFooterConfiguration footerConfiguration;

    public FormReportConfiguration(String outputFilePath, ReportHeaderConfiguration headerConfiguration, ReportFooterConfiguration footerConfiguration) {
        this.outputFilePath = outputFilePath;
        this.headerConfiguration = headerConfiguration;
        this.footerConfiguration = footerConfiguration;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public ReportHeaderConfiguration getHeaderConfiguration() {
        return headerConfiguration;
    }

    public ReportFooterConfiguration getFooterConfiguration() {
        return footerConfiguration;
    }
}
