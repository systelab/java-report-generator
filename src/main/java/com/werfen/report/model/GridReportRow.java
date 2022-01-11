package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class GridReportRow {
    @Singular
    private final List<GridReportField> values;
}
