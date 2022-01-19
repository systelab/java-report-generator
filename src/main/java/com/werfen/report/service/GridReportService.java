package com.werfen.report.service;

import com.werfen.report.model.*;

import java.io.File;

public class GridReportService {

    public File build(GridReportConfiguration gridReportConfiguration, GridPageDataSource gridPageDataSource, ReportFormat reportFormat, PageFormat pageFormat) {

        String filePath = gridReportConfiguration.getOutputFilePath();

        switch (reportFormat) {
            case PDF:
                PdfGridReportService pdfGridReportService = new PdfGridReportService();
                pdfGridReportService.exportToPdf(filePath, gridReportConfiguration, new GridReportDataSource(gridPageDataSource), pageFormat);
                break;
            case EXCEL:
                ExcelGridReportService excelGridReportService = new ExcelGridReportService();
                excelGridReportService.export(filePath, gridReportConfiguration.getHeaderConfiguration().getTitle(), gridReportConfiguration, gridPageDataSource);
                break;
            default:
                throw new RuntimeException("Report Format " + reportFormat + " is not currently supported");

        }
        return new File(filePath);
    }


}
