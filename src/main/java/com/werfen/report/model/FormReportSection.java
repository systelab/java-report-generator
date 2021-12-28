package com.werfen.report.model;

import java.util.ArrayList;
import java.util.List;


public class FormReportSection {
    private final String title;
    private final List<FormReportSection> subsections;
    private final List<FormReportField> fields;

    public String getTitle() {
        return title;
    }

    public List<FormReportSection> getSubsections() {
        return subsections;
    }

    public List<FormReportField> getFields() {
        return fields;
    }

    private FormReportSection(FormReportSectionBuilder builder) {
        this.title = builder.title;
        this.subsections = builder.subsections;
        this.fields = builder.fields;
    }

    public static class FormReportSectionBuilder {
        private String title;
        private List<FormReportSection> subsections = new ArrayList<>();
        private List<FormReportField> fields = new ArrayList<>();

        public FormReportSectionBuilder title(String title){
           this.title = title;
            return this;
        }

        public FormReportSectionBuilder addSubsection(FormReportSection subsection){
            this.subsections.add(subsection);
            return this;
        }
        public FormReportSectionBuilder addField(FormReportField field){
            this.fields.add(field);
            return this;
        }

        public FormReportSection build(){
            return new FormReportSection(this);
        }
    }


}
