package com.werfen.report.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GridReportRow {
    private final List<ReportField> values;

    private GridReportRow(GridReportRowBuilder builder) {
        this.values = builder.values;
    }

    public List<ReportField> getValues() {
        return values;
    }

    public static class GridReportRowBuilder {
        private List<ReportField> values = new ArrayList<>();

        public GridReportRowBuilder addValue(ReportField value) {
            this.values.add(value);
            return this;
        }

        public GridReportRow build() {
            return new GridReportRow(this);
        }
    }
}
