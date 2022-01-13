package com.werfen.report.model;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@RequiredArgsConstructor
public class ListGridPageDataSource implements GridPageDataSource {

    private final int pageSize;
    private final List<GridReportRow> rows;

    private int currentIndex = 0;
    private List<GridReportRow> currentRows;

    @Override
    public void moveFirst() {
        this.currentRows = rows.subList(currentIndex, currentIndex + pageSize);
    }

    @Override
    public boolean nextPage() {
        currentIndex += pageSize;
        if (currentIndex < this.rows.size()) {
            this.currentRows = this.rows.subList(currentIndex, (currentIndex + pageSize) <= this.rows.size() ? currentIndex + pageSize : this.rows.size() - 1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<GridReportRow> getCurrentPageRows() {
        return currentRows;
    }
}
