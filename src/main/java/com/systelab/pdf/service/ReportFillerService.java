package com.systelab.pdf.service;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ReportFillerService {

    private final Map<String, Object> parameters = new HashMap<>();
    Logger log = LoggerFactory.getLogger(ReportFillerService.class);
    private String reportFileName;
    private JasperReport jasperReport;
    private JasperPrint jasperPrint;

    public void prepareReport(InputStream reportStream, Map<String, Object> parameters) {
        compileReport(reportStream);
        fillReport(parameters);
    }

    public void compileReport(InputStream reportStream) {
        try {
            jasperReport = JasperCompileManager.compileReport(reportStream);
        } catch (JRException ex) {
            log.error("", ex);
        }
    }

    public void fillReport(Map<String, Object> parameters) {
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
        } catch (JRException ex) {
            log.error("", ex);
        }
    }
}
