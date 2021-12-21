package com.werfen.report.model;

import java.util.Collections;
import java.util.List;

public class GridReportData {

    private List<GridReportRow> rows;

    public GridReportData(List<GridReportRow> rows) {
        this.rows = Collections.unmodifiableList(rows);
    }

    public List<GridReportRow> getRows() {
        return rows;
    }

}
