package com.werfen.report.service;

import com.werfen.report.model.FormReportConfiguration;
import com.werfen.report.model.FormReportData;
import com.werfen.report.model.PageFormat;
import com.werfen.report.service.template.BaseReportTemplateBuilder;
import com.werfen.report.service.template.FormReportTemplateBuilder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class FormReportService {

    Logger log = Logger.getLogger(GridReportService.class.getName());

    public File build(FormReportConfiguration formReportConfiguration, FormReportData formReportData, PageFormat pageFormat) throws JRException {
        FormReportTemplateBuilder template = new FormReportTemplateBuilder();
        template.initJasperDesign("formReport", pageFormat);
        template.addHeader(formReportConfiguration.getHeaderConfiguration());
        template.addFooter(formReportConfiguration.getFooterConfiguration());
        template.addForm(formReportData);
        this.exportToPdf(formReportConfiguration.getOutputFilePath(), template.getJasperDesign(), getProperties(formReportConfiguration), new JREmptyDataSource());

        return new File(formReportConfiguration.getOutputFilePath());
    }

    private void exportToPdf(String filePath, JasperDesign jasperDesign, Map<String, Object> parameters, JRDataSource ds) throws JRException {

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
        exporter.setConfiguration(getBreakConfiguration());
        exporter.setConfiguration(getSecurityConfiguration());

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.info("Error exporting to PDF: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private SimplePdfReportConfiguration getBreakConfiguration() {
        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);
        return reportConfig;
    }

    private SimplePdfExporterConfiguration getSecurityConfiguration() {
        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");
        return exportConfig;
    }

    private Map<String, Object> getProperties(FormReportConfiguration formReportConfiguration) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(BaseReportTemplateBuilder.TITLE_LOGO_PARAMETER, formReportConfiguration.getHeaderConfiguration().getLogoPath());
        return parameters;
    }

}
