package com.werfen.report.model;

import java.util.ArrayList;
import java.util.List;

public class FormReportField {
    private final String label;
    private final String value;
    private final List<FormReportField> subfields;

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public List<FormReportField> getSubfields() {
        return subfields;
    }

    private FormReportField(FormReportFieldBuilder builder) {
        this.label = builder.label;
        this.value = builder.value;
        this.subfields = builder.subfields;
    }

    public static class FormReportFieldBuilder {
        private String label;
        private String value;
        private List<FormReportField> subfields = new ArrayList<>();

        public FormReportFieldBuilder label(String label){
            this.label = label;
            return this;
        }

        public FormReportFieldBuilder value(String value){
            this.value = value;
            return this;
        }

        public FormReportFieldBuilder addSubfield(FormReportField subfield){
            this.subfields.add(subfield);
            return this;
        }

        public FormReportField build() {
            return new FormReportField(this);
        }
    }
}
