package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportFooterConfiguration {
    private final GridReportField field1;
    private final GridReportField field2;
    private final GridReportField field3;
    private final boolean showPageNumbers = true;
}
