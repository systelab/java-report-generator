package com.werfen.report.model;

public class GridReportSorting {
    private String column;
    private Boolean ascending;

    public GridReportSorting() {

    }

    public GridReportSorting(String column, Boolean ascending) {
        this.column = column;
        this.ascending = ascending;
    }

    public String getColumn() {
        return column;
    }

    public Boolean getAscending() {
        return ascending;
    }
}
