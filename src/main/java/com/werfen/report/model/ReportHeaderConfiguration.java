package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportHeaderConfiguration {
    private final String title;
    private final String logoPath;
    private final ReportField field1;
    private final ReportField field2;
    private final ReportField field3;
    private final ReportField field4;
}
