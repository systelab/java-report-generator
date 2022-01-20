package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReportFooterConfiguration {
    private final GridReportField field1;
    private final GridReportField field2;
    private final GridReportField field3;
    @Builder.Default
    private final boolean showPageNumbers = true;

    public List<GridReportField> getFields() {
        return List.of(field1, field2, field3);
    }
}
