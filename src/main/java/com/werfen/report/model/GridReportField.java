package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GridReportField {
    private final String name;
    private final String value;
}
