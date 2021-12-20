package com.systelab.pdf.model;

public class ReportHeaderConfiguration {
    private final String title;
    private final String logoPath;
    private final ReportField field1;
    private final ReportField field2;
    private final ReportField field3;
    private final ReportField field4;

    public ReportHeaderConfiguration(String title, String logoPath, ReportField field1, ReportField field2, ReportField field3, ReportField field4) {
        this.title = title;
        this.logoPath = logoPath;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }

    public String getTitle() {
        return title;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public ReportField getField1() {
        return field1;
    }

    public ReportField getField2() {
        return field2;
    }

    public ReportField getField3() {
        return field3;
    }

    public ReportField getField4() {
        return field4;
    }
}
