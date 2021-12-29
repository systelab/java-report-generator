package com.werfen.report.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridReportSorting {
    private String column;
    private Boolean ascending;
}
