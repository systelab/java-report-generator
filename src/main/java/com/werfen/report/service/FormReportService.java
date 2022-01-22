package com.werfen.report.service;

import com.werfen.report.model.FormReportConfiguration;
import com.werfen.report.model.FormReportData;
import com.werfen.report.model.PageFormat;
import com.werfen.report.exception.ReportException;
import com.werfen.report.service.pdf.PdfFormReportService;
import net.sf.jasperreports.engine.JRException;

import java.io.File;

public class FormReportService {

    public File build(FormReportConfiguration formReportConfiguration, FormReportData formReportData, PageFormat pageFormat) throws ReportException {
        try {
            String filePath = formReportConfiguration.getOutputFilePath();
            PdfFormReportService pdfFormReportService = new PdfFormReportService();
            pdfFormReportService.export(filePath, formReportConfiguration, formReportData, pageFormat);
            return new File(filePath);
        } catch (JRException ex) {
            throw new ReportException(ex);
        }
    }
}
