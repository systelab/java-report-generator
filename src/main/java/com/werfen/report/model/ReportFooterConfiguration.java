package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportFooterConfiguration {
    private final ReportField field1;
    private final ReportField field2;
    private final ReportField field3;
}
