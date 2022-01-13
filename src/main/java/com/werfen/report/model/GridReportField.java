package com.werfen.report.model;


import com.werfen.report.util.GeneralConfiguration;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.nonNull;


@NoArgsConstructor
@Getter
public class GridReportField {
    private String name;
    private String value;
    private String defaultNullValue;

    public static GridReportField of(String name, String value) {
        return GridReportField.of(name,value, GeneralConfiguration.getDefaultNullString());
    }

    public static GridReportField of(String name, String value, String defaultNullValue) {
        GridReportField gridReportField =  new GridReportField();
        gridReportField.name = name;
        gridReportField.value = value;
        gridReportField.defaultNullValue = defaultNullValue;
        return gridReportField;
    }

    public String getValue() {
        return nonNull(value) ? value : defaultNullValue;
    }
}
