package com.werfen.report.model;

import java.util.Collections;
import java.util.List;

public class GridReportRow {
    private final List<ReportField> values;

    public GridReportRow(List<ReportField> values) {
        this.values = Collections.unmodifiableList(values);
    }

    public List<ReportField> getValues() {
        return values;
    }
}
