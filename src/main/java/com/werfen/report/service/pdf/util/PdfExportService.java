package com.werfen.report.service.pdf.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static java.util.Objects.nonNull;

public class PdfExportService {

    public File exportToFile(JasperPrint jasperPrint, String filePath) throws JRException {
        return exportToFile(jasperPrint, filePath, null);
    }
    public File exportToFile(JasperPrint jasperPrint, String filePath, String password) throws JRException {
        JRPdfExporter exporter = exportDocument(jasperPrint, new SimpleOutputStreamExporterOutput(filePath), password);

        exporter.exportReport();
        return new File(filePath);
    }

    public ByteArrayOutputStream exportToStream(JasperPrint jasperPrint) throws JRException {
        return exportToStream(jasperPrint, null);
    }
    public ByteArrayOutputStream exportToStream(JasperPrint jasperPrint, String password) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(outputStream);
        JRPdfExporter exporter = exportDocument(jasperPrint, simpleOutputStreamExporterOutput, password);

        exporter.exportReport();
        return outputStream;
    }

    private JRPdfExporter exportDocument(JasperPrint jasperPrint, SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput, String password) {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);
        exporter.setConfiguration(getPdfReportConfiguration());
        exporter.setConfiguration(getPdfExporterConfiguration(password));
        return exporter;
    }

    private SimplePdfReportConfiguration getPdfReportConfiguration() {
        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);
        return reportConfig;
    }

    private SimplePdfExporterConfiguration getPdfExporterConfiguration(String password) {
        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setAllowedPermissionsHint("PRINTING");
        if(nonNull(password)){
            exportConfig.setOwnerPassword(password);
        }
        return exportConfig;
    }
}
