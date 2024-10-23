package com.werfen.report.service.pdf.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class PdfExportService {

    public File exportToFile(JasperPrint jasperPrint, String filePath) throws JRException {
        JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
        return new File(filePath);
    }

    public ByteArrayOutputStream exportToStream(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream;
    }
}
