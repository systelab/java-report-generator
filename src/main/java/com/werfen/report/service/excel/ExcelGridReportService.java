package com.werfen.report.service.excel;

import com.werfen.report.model.GridPageDataSource;
import com.werfen.report.model.GridReportConfiguration;
import com.werfen.report.service.excel.template.DataSheetTemplate;
import com.werfen.report.service.excel.template.SummarySheetTemplate;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class ExcelGridReportService {

    Logger log = Logger.getLogger(ExcelGridReportService.class.getName());

    public void export(String filePath, GridReportConfiguration gridReportConfiguration, GridPageDataSource dataSource) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             Workbook workbook = generateWorkbook(gridReportConfiguration, dataSource)) {
            workbook.write(fileOut);
        } catch (IOException ex) {
            log.info("Error exporting to Excel: " + ex.getMessage());
        }
    }

    public ByteArrayOutputStream exportToStream(GridReportConfiguration gridReportConfiguration, GridPageDataSource dataSource) throws IOException {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Workbook workbook = generateWorkbook(gridReportConfiguration, dataSource)) {
            workbook.write(outputStream);
            return outputStream;
        } catch (IOException ex) {
            log.info("Error exporting to Excel: " + ex.getMessage());
        }

        return null;
    }

    private Workbook generateWorkbook(GridReportConfiguration gridReportConfiguration, GridPageDataSource dataSource) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        DataSheetTemplate dataTemplate = new DataSheetTemplate();
        SummarySheetTemplate summaryTemplate = new SummarySheetTemplate();

        dataTemplate.generate(workbook, dataSource, gridReportConfiguration.getGridColumnConfigurations());
        summaryTemplate.generate(workbook, gridReportConfiguration);
        return workbook;
    }


}
