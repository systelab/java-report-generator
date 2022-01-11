package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportHeaderConfiguration {
    private final String title;
    private final String logoPath;
    private final GridReportField field1;
    private final GridReportField field2;
    private final GridReportField field3;
    private final GridReportField field4;
}
