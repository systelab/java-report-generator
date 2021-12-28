package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class GridReportData {
    @Singular
    private final List<GridReportRow> rows;
}
