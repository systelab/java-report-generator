package com.werfen.report.model;

import java.util.ArrayList;
import java.util.List;

public class FormReportField {
    private String label;
    private String value;
    private List<FormReportField> subfields = new ArrayList<>();

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public List<FormReportField> getSubfields() {
        return subfields;
    }
}
