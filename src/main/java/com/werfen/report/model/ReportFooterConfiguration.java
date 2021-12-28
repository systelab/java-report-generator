package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportFooterConfiguration {
    private final String createdAt;
    private final ReportField additionalField1;
    private final ReportField additionalField2;
}
