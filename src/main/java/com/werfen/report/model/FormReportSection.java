package com.werfen.report.model;

import java.util.ArrayList;
import java.util.List;


public class FormReportSection {
    private String title;
    private List<FormReportSection> subsections = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public List<FormReportSection> getSubsections() {
        return subsections;
    }

    public List<FormReportField> getFields() {
        return fields;
    }

    private List<FormReportField> fields = new ArrayList<>();
}
