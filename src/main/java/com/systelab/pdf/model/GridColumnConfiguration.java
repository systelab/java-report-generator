package com.systelab.pdf.model;

public class GridColumnConfiguration {

    private final String name;
    private final GridReportColumnWidth width;
    private final String translationKey;

    public GridColumnConfiguration(String name, GridReportColumnWidth width, String translationKey) {
        this.name = name;
        this.width = width;
        this.translationKey = translationKey;
    }

    public String getName() {
        return name;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public GridReportColumnWidth getWidth() {
        return width;
    }
}
