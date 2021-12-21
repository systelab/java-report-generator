package com.werfen.report.model;

public class GridColumnConfiguration {

    private final String name;
    private final GridReportColumnWidth width;
    private final String translation;

    public GridColumnConfiguration(String name, GridReportColumnWidth width, String translation) {
        this.name = name;
        this.width = width;
        this.translation = translation;
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
}
