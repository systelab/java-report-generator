package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Builder
@Getter
public class GridReportRow {
    @Singular
    private final List<ReportField> values;
}
