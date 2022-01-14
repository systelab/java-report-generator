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

import static java.util.Objects.nonNull;

public class ExcelGridReportService {

    private static final int EXCEL_WITDH_VALUE = 256 * 7;

    public void export(String filePath, String sheetName, GridReportConfiguration gridReportConfiguration, GridPageDataSource dataSource) {
        Workbook workbook = new XSSFWorkbook();

        createDataSheet(dataSource, gridReportConfiguration.getGridColumnConfigurations(), workbook);
        createSummary(gridReportConfiguration, workbook);

        try {
            FileOutputStream fileOut = null;
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException ex){

        }
    }

    private void createDataSheet(GridPageDataSource dataSource, List<GridColumnConfiguration> gridColumnConfigurations, Workbook workbook) {
        Sheet sheet = workbook.createSheet("Data");
        int row = 0;
        Row gridTitleRow = sheet.createRow(0);
        for(int col=0;col<gridColumnConfigurations.size();col++) {
            GridColumnConfiguration gridColumnConfiguration= gridColumnConfigurations.get(col);
            Cell cell = gridTitleRow.createCell(col);
            cell.setCellValue(gridColumnConfiguration.getTranslation());
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setBold(true);
            headerStyle.setFont(font);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(col, gridColumnConfiguration.getWidth().getValue() * EXCEL_WITDH_VALUE);

        }
        dataSource.moveFirst();
        row = createDataRow(dataSource.getCurrentPageRows(),sheet,row + 1 , gridColumnConfigurations);
        while(dataSource.nextPage()){
            row = createDataRow(dataSource.getCurrentPageRows(),sheet,row, gridColumnConfigurations);
        }
    }

    private int createDataRow(List<GridReportRow> currentPageRows, Sheet sheet, int row, List<GridColumnConfiguration> gridColumnConfigurations) {
        for(int rowIterator = 0 ;rowIterator<currentPageRows.size();rowIterator++) {
            Row dataRow = sheet.createRow(row);
            List<GridReportField> values = currentPageRows.get(rowIterator).getValues();
            for(int col=0;col<gridColumnConfigurations.size();col++) {
                GridColumnConfiguration gridColumnConfiguration= gridColumnConfigurations.get(col);
                Cell cell = dataRow.createCell(col);
                cell.setCellValue(getValueFromName(values,gridColumnConfiguration.getName()));
                sheet.setColumnWidth(col, gridColumnConfiguration.getWidth().getValue() * EXCEL_WITDH_VALUE);
            }
            row++;
        }
        return row;
    }

    private String getValueFromName(List<GridReportField> values, String name) {
        Optional<GridReportField> currentReportField = values.stream().filter(reportField -> name.equals(reportField.getName())).findFirst();
        return currentReportField.orElseThrow(() -> new RuntimeException("The field name '" + name + "' in the XLSX is not valid")).getValue();
    }



    private void createSummary(GridReportConfiguration gridReportConfiguration, Workbook workbook)  {
        try {
            ReportHeaderConfiguration headerConfiguration = gridReportConfiguration.getHeaderConfiguration();
            ReportFooterConfiguration reportFooterConfiguration = gridReportConfiguration.getFooterConfiguration();
            Sheet sheet = workbook.createSheet("Summary");
            Row header = sheet.createRow(0);
            Cell headerCell  = header.createCell(0) ;
            headerCell.setCellValue(headerConfiguration.getTitle());
            FileInputStream inputStream = new FileInputStream( headerConfiguration.getLogoPath() );

            int pictureIndex = workbook.addPicture(IOUtils.toByteArray(inputStream), Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(2);
            anchor.setRow1(0);
            anchor.setCol2(4);
            anchor.setRow2(1);
            Picture pict = drawing.createPicture(anchor, pictureIndex);
            header.createCell(2) ;

            int row = 2;


            if (nonNull(headerConfiguration.getField1())) {
                createHeaderFieldsData(headerConfiguration.getField1(), sheet, row);
                row ++;
            }
            if (nonNull(headerConfiguration.getField2())) {
                createHeaderFieldsData(headerConfiguration.getField2(), sheet, row);
                row++;
            }
            if (nonNull(headerConfiguration.getField3())) {
                createHeaderFieldsData(headerConfiguration.getField3(), sheet, row);
                row ++;
            }
            if (nonNull(headerConfiguration.getField4())) {
                createHeaderFieldsData(headerConfiguration.getField4(), sheet, row);
                row ++;
            }
            row = row + 2;

            if (nonNull(reportFooterConfiguration.getField1())) {
                createHeaderFieldsData(reportFooterConfiguration.getField1(), sheet, row);
                row++;
            }
            if (nonNull(reportFooterConfiguration.getField2())) {
                createHeaderFieldsData(reportFooterConfiguration.getField2(), sheet, row);
                row++;
            }
            if (nonNull(reportFooterConfiguration.getField3())) {
                createHeaderFieldsData(reportFooterConfiguration.getField3(), sheet, row);
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createHeaderFieldsData(GridReportField gridReportField, Sheet sheet, int row) {
        Row subHeaderFiledRow = sheet.createRow(row);
        Cell headrFieldCell = subHeaderFiledRow.createCell(0);
        headrFieldCell.setCellValue(gridReportField.getName());
        headrFieldCell = subHeaderFiledRow.createCell(1);
        headrFieldCell.setCellValue(gridReportField.getValue());
    }

}
