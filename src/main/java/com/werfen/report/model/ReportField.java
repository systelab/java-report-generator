package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportField {
    private final String name;
    private final String value;
}
