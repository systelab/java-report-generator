package com.werfen.report.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Builder
@Getter
public class ReportFooterConfiguration {
    private final GridReportField field1;
    private final GridReportField field2;
    private final GridReportField field3;
    @Builder.Default
    private final boolean showPageNumbers = true;

    public List<GridReportField> getFields() {
        List<GridReportField> fields = new ArrayList<>();
        if (nonNull(this.getField1()))
            fields.add(this.field1);
        if (nonNull(this.getField2()))
            fields.add(this.field2);
        if (nonNull(this.getField3()))
            fields.add(this.field3);

        return fields;
    }
}
