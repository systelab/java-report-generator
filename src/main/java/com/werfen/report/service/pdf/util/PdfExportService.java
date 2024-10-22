package com.werfen.report.service.pdf.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;
import net.sf.jasperreports.pdf.SimplePdfReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class PdfExportService {

    public File exportToFile(JasperPrint jasperPrint, String filePath) throws JRException {
        JRPdfExporter exporter = exportDocument(jasperPrint, new SimpleOutputStreamExporterOutput(filePath));

        exporter.exportReport();
        return new File(filePath);
    }

    public ByteArrayOutputStream exportToStream(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(outputStream);
        JRPdfExporter exporter = exportDocument(jasperPrint, simpleOutputStreamExporterOutput);

        exporter.exportReport();
        return outputStream;
    }

    private JRPdfExporter exportDocument(JasperPrint jasperPrint, SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput) {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);
        exporter.setConfiguration(getPdfReportConfiguration());
        exporter.setConfiguration(getPdfExporterConfiguration());
        return exporter;
    }

    private SimplePdfReportConfiguration getPdfReportConfiguration() {
        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);
        return reportConfig;
    }

    private SimplePdfExporterConfiguration getPdfExporterConfiguration() {
        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setAllowedPermissionsHint("PRINTING");
        return exportConfig;
    }
}
