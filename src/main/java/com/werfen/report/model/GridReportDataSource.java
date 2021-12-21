package com.werfen.report.model;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

import java.util.Iterator;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class GridReportDataSource extends JRAbstractBeanDataSource {

    private final GridReportData rows;
    private Iterator<GridReportRow> rowIterator;
    private GridReportRow currentRow;

    public GridReportDataSource(GridReportData rows) {
        super(true);
        this.rows = rows;
        if (nonNull(rows)) {
            this.rowIterator = this.rows.getRows().listIterator();
        }
    }

    @Override
    public void moveFirst() {
        if (nonNull(rows)) {
            rowIterator = rows.getRows().listIterator();
        }
    }

    @Override
    public boolean next() throws JRException {
        boolean hasNext = false;
        if (nonNull(rowIterator)) {
            hasNext = rowIterator.hasNext();
            if (hasNext) {
                this.currentRow = rowIterator.next();
            }
        }
        return hasNext;
    }

    @Override
    public Object getFieldValue(JRField jrField) {
        String name = jrField.getName();

        Optional<ReportField> currentReportField = this.currentRow.getValues().stream().filter(reportField -> name.equals(reportField.getName())).findFirst();
        return currentReportField.orElseThrow(() -> new RuntimeException("The field name '" + name + "' in the Jasper Report is not valid")).getValue();
    }
}
