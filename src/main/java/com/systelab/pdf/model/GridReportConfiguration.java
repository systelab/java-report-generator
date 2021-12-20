package com.systelab.pdf.model;

import java.util.Collections;
import java.util.List;

public class GridReportConfiguration {

    private final String outputFilePath;
    private final ReportHeaderConfiguration headerConfiguration;
    private final ReportFooterConfiguration footerConfiguration;
    private final List<GridColumnConfiguration> gridColumnConfigurations;

    public GridReportConfiguration(String outputFilePath, ReportHeaderConfiguration headerConfiguration,
                                   ReportFooterConfiguration footerConfiguration, List<GridColumnConfiguration> gridColumnConfigurations) {
        this.outputFilePath = outputFilePath;
        this.headerConfiguration = headerConfiguration;
        this.footerConfiguration = footerConfiguration;
        this.gridColumnConfigurations = Collections.unmodifiableList(gridColumnConfigurations);
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

    public List<GridColumnConfiguration> getGridColumnConfigurations() {
        return gridColumnConfigurations;
    }

}
