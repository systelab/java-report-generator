package com.werfen.report.service;

import com.werfen.report.model.*;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

public class ExcelGridReportService {

    private static final int BASE_COLUMN_WIDTH = 256 * 7;
    Logger log = Logger.getLogger(ExcelGridReportService.class.getName());

    public void export(String filePath, String sheetName, GridReportConfiguration gridReportConfiguration, GridPageDataSource dataSource) {
        Workbook workbook = new XSSFWorkbook();

        addDataSheet(workbook, dataSource, gridReportConfiguration.getGridColumnConfigurations());
        addSummarySheet(workbook, gridReportConfiguration);

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException ex) {
            log.info("Error exporting to Excel: " + ex.getMessage());
        }
    }

    private void addDataSheet(Workbook workbook, GridPageDataSource dataSource, List<GridColumnConfiguration> gridColumnConfigurations) {
        Sheet sheet = workbook.createSheet("Data");
        addDataSheetHeader(sheet, workbook, gridColumnConfigurations);
        addDataSheetBody(sheet, dataSource, gridColumnConfigurations);
    }

    private void addDataSheetHeader(Sheet sheet, Workbook workbook, List<GridColumnConfiguration> gridColumnConfigurations) {
        Row gridTitleRow = sheet.createRow(0);
        CellStyle headerStyle = getDataSheetHeaderStyle(workbook);
        for (int col = 0; col < gridColumnConfigurations.size(); col++) {
            GridColumnConfiguration gridColumnConfiguration = gridColumnConfigurations.get(col);
            Cell cell = gridTitleRow.createCell(col);
            cell.setCellValue(gridColumnConfiguration.getTranslation());
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(col, gridColumnConfiguration.getWidth().getValue() * BASE_COLUMN_WIDTH);
        }
    }

    private CellStyle getDataSheetHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private void addDataSheetBody(Sheet sheet, GridPageDataSource dataSource, List<GridColumnConfiguration> gridColumnConfigurations) {
        int rowIndex = 1;
        dataSource.moveFirst();
        do {
            List<GridReportRow> pageRows = dataSource.getCurrentPageRows();
            for (GridReportRow row : pageRows) {
                addDataSheetBodyRow(sheet, rowIndex, row, gridColumnConfigurations);
                rowIndex++;
            }
        } while (dataSource.nextPage());
    }

    private void addDataSheetBodyRow(Sheet sheet, int rowIndex, GridReportRow row, List<GridColumnConfiguration> gridColumnConfigurations) {

        Row dataRow = sheet.createRow(rowIndex);
        List<GridReportField> values = row.getValues();
        for (int col = 0; col < gridColumnConfigurations.size(); col++) {
            GridColumnConfiguration gridColumnConfiguration = gridColumnConfigurations.get(col);
            Cell cell = dataRow.createCell(col);
            cell.setCellValue(getGridReportFieldValue(values, gridColumnConfiguration.getName()));
            sheet.setColumnWidth(col, gridColumnConfiguration.getWidth().getValue() * BASE_COLUMN_WIDTH);
        }
    }

    private String getGridReportFieldValue(List<GridReportField> values, String name) {
        Optional<GridReportField> currentReportField = values.stream().filter(reportField -> name.equals(reportField.getName())).findFirst();
        return currentReportField.orElseThrow(() -> new RuntimeException("The field name '" + name + "' in the XLSX is not valid")).getValue();
    }

    private void addSummarySheet(Workbook workbook, GridReportConfiguration gridReportConfiguration) {
        try {
            Sheet sheet = workbook.createSheet("Summary");

            int nextRowIndex = addSummaryTitle(sheet, workbook, gridReportConfiguration.getHeaderConfiguration());
            nextRowIndex = addSummaryHeaderFields(sheet, nextRowIndex, gridReportConfiguration.getHeaderConfiguration());
            addSummaryFooterFields(sheet, nextRowIndex, gridReportConfiguration.getFooterConfiguration());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int addSummaryTitle(Sheet sheet, Workbook workbook, ReportHeaderConfiguration headerConfiguration) throws IOException {
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

    private int addSummaryHeaderFields(Sheet sheet, int rowIndex, ReportHeaderConfiguration headerConfiguration) {
        for (GridReportField field : headerConfiguration.getFields()) {
            if (nonNull(field)) {
                addSummaryField(sheet, rowIndex, field);
                rowIndex++;
            }
        }
        return rowIndex + 2;
    }

    private void addSummaryFooterFields(Sheet sheet, int rowIndex, ReportFooterConfiguration reportFooterConfiguration) {
        for (GridReportField field : reportFooterConfiguration.getFields()) {
            if (nonNull(field)) {
                addSummaryField(sheet, rowIndex, field);
                rowIndex++;
            }
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
