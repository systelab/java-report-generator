package com.werfen.report.model;

import java.util.List;

public interface GridPageDataSource {
    void moveFirst();

    boolean nextPage();

    List<GridReportRow> getCurrentPageRows();
}
