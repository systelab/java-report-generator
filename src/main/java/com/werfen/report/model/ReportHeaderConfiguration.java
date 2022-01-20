package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReportHeaderConfiguration {
    private final String title;
    private final String logoPath;
    private final GridReportField field1;
    private final GridReportField field2;
    private final GridReportField field3;
    private final GridReportField field4;

    public List<GridReportField> getFields() {
        return List.of(field1, field2, field3, field4);
    }
}
