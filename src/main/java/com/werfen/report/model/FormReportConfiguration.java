package com.werfen.report.model;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
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



}
