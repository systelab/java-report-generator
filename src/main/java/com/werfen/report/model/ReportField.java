package com.werfen.report.model;

public class ReportField {
    private String name;
    private String value;

    public static ReportField of(String name, String value) {
        ReportField reportField=new ReportField();
        reportField.name = name;
        reportField.value = value;
        return reportField;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
