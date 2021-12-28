package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class FormReportSection {
    private final String title;
    @Singular
    private final List<FormReportSection> subsections;
    @Singular
    private final List<FormReportField> fields;
}
