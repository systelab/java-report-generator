package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class GridReportConfiguration {
    private final String outputFilePath;
    private final ReportHeaderConfiguration headerConfiguration;
    private final ReportFooterConfiguration footerConfiguration;
    @Singular
    private final List<GridColumnConfiguration> gridColumnConfigurations;
}
