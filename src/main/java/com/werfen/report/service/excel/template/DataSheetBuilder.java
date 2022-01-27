package com.werfen.report.service.excel.template;

import com.werfen.report.model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Optional;

public class DataSheetBuilder {
    private static final int BASE_COLUMN_WIDTH = 256 * 7;

    public void build(Workbook workbook, GridPageDataSource dataSource, List<GridColumnConfiguration> gridColumnConfigurations) {
        Sheet sheet = workbook.createSheet("Data");
        addHeader(sheet, workbook, gridColumnConfigurations);
        addBody(sheet, dataSource, gridColumnConfigurations);
    }

    private void addHeader(Sheet sheet, Workbook workbook, List<GridColumnConfiguration> gridColumnConfigurations) {
        Row row = sheet.createRow(0);
        CellStyle headerStyle = getHeaderCellStyle(workbook);
        for (int col = 0; col < gridColumnConfigurations.size(); col++) {
            createCell(sheet, row, col, gridColumnConfigurations.get(col).getTranslation(), gridColumnConfigurations.get(col).getWidth(), headerStyle);
        }
    }

    private void addBody(Sheet sheet, GridPageDataSource dataSource, List<GridColumnConfiguration> gridColumnConfigurations) {
        int rowIndex = 1;
        dataSource.moveFirst();
        do {
            for (GridReportRow row : dataSource.getCurrentPageRows()) {
                addRow(sheet, rowIndex, row, gridColumnConfigurations);
                rowIndex++;
            }
        } while (dataSource.nextPage());
    }

    private void addRow(Sheet sheet, int rowIndex, GridReportRow row, List<GridColumnConfiguration> gridColumnConfigurations) {
        Row dataRow = sheet.createRow(rowIndex);
        List<GridReportField> values = row.getValues();
        for (int col = 0; col < gridColumnConfigurations.size(); col++) {
            GridColumnConfiguration gridColumnConfiguration = gridColumnConfigurations.get(col);
            createCell(sheet, dataRow, col, getGridReportFieldValue(values, gridColumnConfiguration.getName()), gridColumnConfiguration.getWidth());
        }
    }

    private Cell createCell(Sheet sheet, Row row, int col, String value, GridReportColumnWidth width) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        sheet.setColumnWidth(col, width.getValue() * BASE_COLUMN_WIDTH);
        return cell;
    }

    private Cell createCell(Sheet sheet, Row row, int col, String value, GridReportColumnWidth width, CellStyle headerStyle) {
        Cell cell = createCell(sheet, row, col, value, width);
        cell.setCellStyle(headerStyle);
        return cell;
    }


    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private String getGridReportFieldValue(List<GridReportField> values, String name) {
        Optional<GridReportField> currentReportField = values.stream().filter(reportField -> name.equals(reportField.getName())).findFirst();
        return currentReportField.orElseThrow(() -> new RuntimeException("The field name '" + name + "' in the XLSX is not valid")).getValue();
    }

}
