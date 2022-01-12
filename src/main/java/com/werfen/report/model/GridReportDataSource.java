package com.werfen.report.model;

import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

import java.util.Iterator;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class GridReportDataSource extends JRAbstractBeanDataSource {

    private final GridPageDataSource dataSource;
    private Iterator<GridReportRow> rowIterator;
    private GridReportRow currentRow;

    public GridReportDataSource(GridPageDataSource dataSource) {
        super(true);
        this.dataSource = dataSource;
    }


    @Override
    public void moveFirst() {
        if (dataSource.getRowCount() > 0) {
            this.dataSource.moveFirst();
            rowIterator = this.dataSource.getCurrentPageRows().listIterator();
        }
    }

    @Override
    public boolean next() {
        boolean hasNext;
        if (nonNull(rowIterator)) {
            hasNext = rowIterator.hasNext();
            if (hasNext) {
                this.currentRow = rowIterator.next();
            }
        } else {
            this.dataSource.moveFirst();
            this.rowIterator = this.dataSource.getCurrentPageRows().listIterator();
            hasNext = this.rowIterator.hasNext();
            if (hasNext) {
                this.currentRow = rowIterator.next();
            }
        }
        return hasNext;
    }

    @Override
    public Object getFieldValue(JRField jrField) {
        String name = jrField.getName();

        Optional<GridReportField> currentReportField = this.currentRow.getValues().stream().filter(reportField -> name.equals(reportField.getName())).findFirst();
        return currentReportField.orElseThrow(() -> new RuntimeException("The field name '" + name + "' in the Jasper Report is not valid")).getValue();
    }
}
