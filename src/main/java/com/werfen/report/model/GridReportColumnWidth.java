package com.werfen.report.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GridReportColumnWidth {

    COLUMN_WIDTH_1(1),
    COLUMN_WIDTH_2(2),
    COLUMN_WIDTH_3(3),
    COLUMN_WIDTH_4(4),
    COLUMN_WIDTH_5(5),
    COLUMN_WIDTH_6(6),
    COLUMN_WIDTH_7(7),
    COLUMN_WIDTH_8(8),
    COLUMN_WIDTH_9(9),
    COLUMN_WIDTH_10(10),
    COLUMN_WIDTH_11(11),
    COLUMN_WIDTH_12(12);

    private final int value;

    public static GridReportColumnWidth findByValue(int value) {
        for (GridReportColumnWidth v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        return null;
    }
}
