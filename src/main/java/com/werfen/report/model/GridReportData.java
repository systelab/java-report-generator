package com.werfen.report.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridReportData {

    private final List<GridReportRow> rows;

    private GridReportData(GridReportDataBuilder builder) {
        this.rows = builder.rows;
    }

    public List<GridReportRow> getRows() {
        return rows;
    }

    public static class GridReportDataBuilder {
        private List<GridReportRow> rows = new ArrayList<>();

        public GridReportDataBuilder addRow(GridReportRow row) {
            this.rows.add(row);
            return this;
        }

        public GridReportData build() {
            return new GridReportData(this);
        }
    }
}
