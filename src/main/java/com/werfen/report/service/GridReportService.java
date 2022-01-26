package com.werfen.report.service;

import com.werfen.report.exception.ReportException;
import com.werfen.report.exception.ReportFormatException;
import com.werfen.report.model.GridPageDataSource;
import com.werfen.report.model.GridReportConfiguration;
import com.werfen.report.model.PageFormat;
import com.werfen.report.model.ReportFormat;
import com.werfen.report.service.excel.ExcelGridReportService;
import com.werfen.report.service.pdf.PdfGridReportService;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;

public class GridReportService {

    public File build(GridReportConfiguration gridReportConfiguration, GridPageDataSource gridPageDataSource, ReportFormat reportFormat, PageFormat pageFormat) throws ReportException, ReportFormatException {
        if (reportFormat == ReportFormat.PDF)
            return buildPDF(gridReportConfiguration, gridPageDataSource, pageFormat);
        else
            return buildExcel(gridReportConfiguration, gridPageDataSource);
    }

    private File buildPDF(GridReportConfiguration gridReportConfiguration, GridPageDataSource gridPageDataSource, PageFormat pageFormat) throws ReportException {
        try {
            String filePath = gridReportConfiguration.getOutputFilePath();
            PdfGridReportService pdfGridReportService = new PdfGridReportService();
            pdfGridReportService.export(filePath, gridReportConfiguration, gridPageDataSource, pageFormat);
            return new File(filePath);
        } catch (JRException ex) {
            throw new ReportException(ex);
        }
    }

    private File buildExcel(GridReportConfiguration gridReportConfiguration, GridPageDataSource gridPageDataSource) throws ReportException {
        try {
            String filePath = gridReportConfiguration.getOutputFilePath();
            ExcelGridReportService excelGridReportService = new ExcelGridReportService();
            excelGridReportService.export(filePath, gridReportConfiguration, gridPageDataSource);
            return new File(filePath);
        } catch (IOException ex) {
            throw new ReportException(ex);
        }
    }
}