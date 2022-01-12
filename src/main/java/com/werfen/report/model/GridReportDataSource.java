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
        if (this.dataSource.getRowCount() > 0) {
            this.dataSource.moveFirst();
        }
    }


    @Override
    public void moveFirst() {
        if (dataSource.getRowCount() > 0) {
            this.dataSource.moveFirst();
            this.rowIterator = this.dataSource.getCurrentPageRows().listIterator();
            this.currentRow = this.rowIterator.next();
        }
    }

    @Override
    public boolean next() {
        if (nonNull(this.rowIterator) && this.rowIterator.hasNext()) {
            this.currentRow = this.rowIterator.next();
            return true;
        } else if (this.dataSource.nextPage()) {
            this.rowIterator = this.dataSource.getCurrentPageRows().listIterator();
            this.currentRow = this.rowIterator.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getFieldValue(JRField jrField) {
        String name = jrField.getName();

        Optional<GridReportField> currentReportField = this.currentRow.getValues().stream().filter(reportField -> name.equals(reportField.getName())).findFirst();
        return currentReportField.orElseThrow(() -> new RuntimeException("The field name '" + name + "' in the Jasper Report is not valid")).getValue();
    }
}
