package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GridColumnConfiguration {
    private final String name;
    private final GridReportColumnWidth width;
    private final String translation;
}
