package com.systelab.pdf.service;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class ReportExporterService {

    Logger log = LoggerFactory.getLogger(ReportExporterService.class);

    public void exportToXlsx(String fileName, String path, String sheetName, InputStream reportStream, Map<String, Object> parameters, JRDataSource ds) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + "/" + fileName + ".xlsx"));

        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[]{sheetName});

        exporter.setConfiguration(reportConfig);

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error generating xls", ex);
        }
    }

    public void exportToCsv(String fileName, JasperPrint jasperPrint) {
        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(fileName));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error generating csv", ex);
        }
    }

    public void exportToHtml(String fileName, JasperPrint jasperPrint) {
        HtmlExporter exporter = new HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(fileName));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error generating HTML", ex);
        }
    }

    public JasperPrint prepareReport(InputStream reportStream, Map<String, Object> parameters) {
        return fillReport(compileReport(reportStream), parameters);
    }

    public JasperReport compileReport(InputStream reportStream) {
        try {
            return JasperCompileManager.compileReport(reportStream);
        } catch (JRException ex) {
            log.error("", ex);
            return null;
        }
    }

    public JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters) {
        try {
            return JasperFillManager.fillReport(jasperReport, parameters);
        } catch (JRException ex) {
            log.error("", ex);
            return null;
        }
    }
}
