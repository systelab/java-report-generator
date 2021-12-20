package com.systelab.pdf.model;

public class GridReportSorting {
    private final String column;
    private final Boolean ascending;

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
