package com.werfen.report.model;

public class GridColumnConfiguration {

    private final String name;
    private final GridReportColumnWidth width;
    private final String translation;

    private GridColumnConfiguration(GridColumnConfigurationBuilder builder) {
        this.name = builder.name;
        this.width = builder.width;
        this.translation = builder.translation;
    }

    public String getName() {
        return name;
    }

    public String getTranslation() {
        return translation;
    }

    public GridReportColumnWidth getWidth() {
        return width;
    }

    public static class GridColumnConfigurationBuilder {
        private String name;
        private GridReportColumnWidth width;
        private String translation;

        public GridColumnConfigurationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GridColumnConfigurationBuilder width(GridReportColumnWidth width) {
            this.width = width;
            return this;
        }

        public GridColumnConfigurationBuilder translation(String translation) {
            this.translation = translation;
            return this;
        }

        public GridColumnConfiguration build() {
            return new GridColumnConfiguration(this);
        }
    }
}
