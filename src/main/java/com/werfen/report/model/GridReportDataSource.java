package com.werfen.report.model;

import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

import java.util.Iterator;
import java.util.Optional;

public class GridReportDataSource extends JRAbstractBeanDataSource {

    private final GridPageDataSource dataSource;
    private Iterator<GridReportRow> currentPageRowIterator;
    private GridReportRow currentRow;

    public GridReportDataSource(GridPageDataSource dataSource) {
        super(true);
        this.dataSource = dataSource;
        this.moveFirst();
    }


    @Override
    public void moveFirst() {
        this.dataSource.moveFirst();
        this.currentPageRowIterator = this.dataSource.getCurrentPageRows().listIterator();
    }

    @Override
    public boolean next() {
        if (this.currentPageRowIterator.hasNext()) {
            this.currentRow = this.currentPageRowIterator.next();
            return true;
        } else if (this.dataSource.nextPage()) {
            this.currentPageRowIterator = this.dataSource.getCurrentPageRows().listIterator();
            this.currentRow = this.currentPageRowIterator.next();
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
