package com.werfen.report.service;

import com.werfen.report.model.GridReportConfiguration;
import com.werfen.report.model.GridReportData;
import com.werfen.report.model.GridReportDataSource;
import com.werfen.report.model.PageFormat;
import com.werfen.report.service.template.BaseReportTemplateBuilder;
import com.werfen.report.service.template.GridReportTemplateBuilder;
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

public class GridReportService {

    Logger log = Logger.getLogger(GridReportService.class.getName());

    public File build(GridReportConfiguration gridReportConfiguration, GridReportData gridReportData) throws JRException {

        GridReportTemplateBuilder template = new GridReportTemplateBuilder();

        template.initJasperDesign("gridReport", PageFormat.A4);
        template.addHeader(gridReportConfiguration.getHeaderConfiguration());
        template.addFooter(gridReportConfiguration.getFooterConfiguration());
        template.addGrid(gridReportConfiguration.getGridColumnConfigurations());
        this.exportToPdf(gridReportConfiguration.getOutputFilePath(), template.getJasperDesign(), getProperties(gridReportConfiguration), new GridReportDataSource(gridReportData));
        return new File(gridReportConfiguration.getOutputFilePath() + ".pdf");
    }

    private void exportToPdf(String filePath, JasperDesign jasperDesign, Map<String, Object> parameters, JRDataSource ds) throws JRException {

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath + ".pdf"));
        exporter.setConfiguration(getBreakConfiguration());
        exporter.setConfiguration(getSecurityConfiguration());

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.info("Error exporting to PDF");
            ex.printStackTrace();
        }
    }

    private Map<String, Object> getProperties(GridReportConfiguration gridReportConfiguration) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(BaseReportTemplateBuilder.TITLE_LOGO_PARAMETER, gridReportConfiguration.getHeaderConfiguration().getLogoPath());
        return parameters;
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

}
