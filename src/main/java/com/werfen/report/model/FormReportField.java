package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class FormReportField {
    private final String label;
    private final String value;
    @Singular
    private final List<FormReportField> subfields;
}
