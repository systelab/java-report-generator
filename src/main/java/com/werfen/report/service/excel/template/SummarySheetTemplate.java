package com.werfen.report.service.excel.template;

import com.werfen.report.model.GridReportConfiguration;
import com.werfen.report.model.GridReportField;
import com.werfen.report.model.ReportFooterConfiguration;
import com.werfen.report.model.ReportHeaderConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class SummarySheetTemplate {

    public void generate(Workbook workbook, GridReportConfiguration gridReportConfiguration) throws IOException {
        Sheet sheet = workbook.createSheet("Summary");

        int nextRowIndex = addTitle(sheet, workbook, gridReportConfiguration.getHeaderConfiguration());
        nextRowIndex = addHeader(sheet, nextRowIndex, gridReportConfiguration.getHeaderConfiguration());
        addFooter(sheet, nextRowIndex, gridReportConfiguration.getFooterConfiguration());
    }

    private int addTitle(Sheet sheet, Workbook workbook, ReportHeaderConfiguration headerConfiguration) throws IOException {
        Row header = sheet.createRow(0);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue(headerConfiguration.getTitle());
        FileInputStream inputStream = new FileInputStream(headerConfiguration.getLogoPath());

        int pictureIndex = workbook.addPicture(IOUtils.toByteArray(inputStream), Workbook.PICTURE_TYPE_PNG);
        inputStream.close();

        CreationHelper helper = workbook.getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(2);
        anchor.setRow1(0);
        anchor.setCol2(4);
        anchor.setRow2(1);
        drawing.createPicture(anchor, pictureIndex);
        header.createCell(2);

        return 2;
    }

    private int addHeader(Sheet sheet, int rowIndex, ReportHeaderConfiguration headerConfiguration) {
        for (GridReportField field : headerConfiguration.getFields()) {
            addSummaryField(sheet, rowIndex, field);
            rowIndex++;
        }
        return rowIndex + 2;
    }

    private void addFooter(Sheet sheet, int rowIndex, ReportFooterConfiguration reportFooterConfiguration) {
        for (GridReportField field : reportFooterConfiguration.getFields()) {
            addSummaryField(sheet, rowIndex, field);
            rowIndex++;
        }
    }

    private void addSummaryField(Sheet sheet, int rowIndex, GridReportField gridReportField) {
        Row subHeaderFiledRow = sheet.createRow(rowIndex);
        Cell headerFieldCell = subHeaderFiledRow.createCell(0);
        headerFieldCell.setCellValue(gridReportField.getName());
        headerFieldCell = subHeaderFiledRow.createCell(1);
        headerFieldCell.setCellValue(gridReportField.getValue());
    }
}
