package com.werfen.report.model;

import java.util.ArrayList;
import java.util.List;

public class FormReportData {

    private final List<FormReportSection> sections;

    public List<FormReportSection> getSections() {
        return sections;
    }

    public FormReportData(FormReportDataBuilder builder) {
        this.sections = builder.sections;
    }

    public static class FormReportDataBuilder{
        private List<FormReportSection> sections = new ArrayList<>();

        public FormReportDataBuilder addSection(FormReportSection section) {
            this.sections.add(section);
            return this;
        }

        public FormReportData build() {
            return new FormReportData(this);
        }
    }


}
