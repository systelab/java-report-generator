package com.werfen.report.service.excel;

import com.werfen.report.model.*;
import com.werfen.report.service.excel.template.DataSheetBuilder;
import com.werfen.report.service.excel.template.SummarySheetBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class ExcelGridReportService {

    Logger log = Logger.getLogger(ExcelGridReportService.class.getName());

    public void export(String filePath, GridReportConfiguration gridReportConfiguration, GridPageDataSource dataSource) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        DataSheetBuilder dataTemplate=new DataSheetBuilder();
        SummarySheetBuilder summaryTemplate=new SummarySheetBuilder();

        dataTemplate.build(workbook, dataSource, gridReportConfiguration.getGridColumnConfigurations());
        summaryTemplate.build(workbook, gridReportConfiguration);

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException ex) {
            log.info("Error exporting to Excel: " + ex.getMessage());
        }
    }


}
