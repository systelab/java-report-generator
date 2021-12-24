package com.werfen.report.service;

import com.werfen.report.model.FormReportConfiguration;
import com.werfen.report.model.FormReportContent;
import com.werfen.report.model.PageFormat;
import com.werfen.report.service.GridReportService;
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

    private FormReportContent content;
    private FormReportConfiguration configuration;

    public void setContent(FormReportContent content) {
        this.content = content;
    }

    public void setConfiguration(FormReportConfiguration configuration) {
        this.configuration = configuration;
    }

    public File buildPDF() {
        try {
            JasperDesign designTemplate = this.buildReportTemplate();
            JasperReport compiledTemplate = JasperCompileManager.compileReport(designTemplate);
            JasperPrint printTemplate = this.fillReportTemplate(compiledTemplate);
            return this.buildReportPDFFile(printTemplate);
        } catch (JRException ex) {
            log.info("Error generating PDF report");
            return null;
        }
    }

    private JasperDesign buildReportTemplate() throws JRException {
        FormReportTemplateBuilder templateBuilder = new FormReportTemplateBuilder();
        templateBuilder.initJasperDesign("formReport", PageFormat.A4);
        templateBuilder.addHeader(this.configuration.getHeaderConfiguration());
        templateBuilder.addFooter(this.configuration.getFooterConfiguration());
        templateBuilder.addForm(this.content);
        return templateBuilder.getJasperDesign();
    }

    JasperPrint fillReportTemplate(JasperReport compiledTemplate) throws JRException {
        Map<String, Object> parametersMap = this.getParametersMap();
        JRDataSource emptyDataSource = new JREmptyDataSource();
        return JasperFillManager.fillReport(compiledTemplate, parametersMap, emptyDataSource);
    }

    private Map<String, Object> getParametersMap() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(BaseReportTemplateBuilder.TITLE_LOGO_PARAMETER, configuration.getHeaderConfiguration().getLogoPath());
        return parameters;
    }

    private File buildReportPDFFile(JasperPrint printTemplate) throws JRException {
        String outputFilePath = this.configuration.getOutputFilePath();

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(printTemplate));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFilePath));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);
        exporter.setConfiguration(reportConfig);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");
        exporter.setConfiguration(exportConfig);

        exporter.exportReport();

        return new File(outputFilePath);
    }

}
