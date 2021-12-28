package com.werfen.report.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridReportConfiguration {

    private final String outputFilePath;
    private final ReportHeaderConfiguration headerConfiguration;
    private final ReportFooterConfiguration footerConfiguration;
    private final List<GridColumnConfiguration> gridColumnConfigurations;

    private GridReportConfiguration(GridReportConfigurationBuilder builder) {
        this.outputFilePath = builder.outputFilePath;
        this.headerConfiguration = builder.headerConfiguration;
        this.footerConfiguration = builder.footerConfiguration;
        this.gridColumnConfigurations = builder.gridColumnConfigurations;
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

    public static class GridReportConfigurationBuilder {
        private String outputFilePath;
        private ReportHeaderConfiguration headerConfiguration;
        private ReportFooterConfiguration footerConfiguration;
        private List<GridColumnConfiguration> gridColumnConfigurations = new ArrayList<>();

        public GridReportConfigurationBuilder outputFilePath(String outputFilePath) {
            this.outputFilePath = outputFilePath;
            return this;
        }

        public GridReportConfigurationBuilder headerConfiguration(ReportHeaderConfiguration headerConfiguration) {
            this.headerConfiguration = headerConfiguration;
            return this;
        }

        public GridReportConfigurationBuilder footerConfiguration(ReportFooterConfiguration footerConfiguration) {
            this.footerConfiguration = footerConfiguration;
            return this;
        }

        public GridReportConfigurationBuilder addGridColumnConfiguration(GridColumnConfiguration gridColumnConfiguration) {
            this.gridColumnConfigurations.add(gridColumnConfiguration);
            return this;
        }

        public GridReportConfiguration build() {
            return new GridReportConfiguration(this);
        }

    }

}
