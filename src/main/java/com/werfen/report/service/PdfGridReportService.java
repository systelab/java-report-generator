package com.werfen.report.service;

import com.werfen.report.model.GridReportConfiguration;
import com.werfen.report.model.PageFormat;
import com.werfen.report.service.template.BaseReportTemplateBuilder;
import com.werfen.report.service.template.GridReportTemplateBuilder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PdfGridReportService {

    Logger log = Logger.getLogger(GridReportService.class.getName());

    public void exportToPdf(String filePath, GridReportConfiguration gridReportConfiguration, JRDataSource ds, PageFormat pageFormat) {

        try {
            GridReportTemplateBuilder template = new GridReportTemplateBuilder();
            template.initJasperDesign("gridReport", pageFormat);
            template.addHeader(gridReportConfiguration.getHeaderConfiguration());
            template.addFooter(gridReportConfiguration.getFooterConfiguration());
            template.addGrid(gridReportConfiguration.getGridColumnConfigurations());

            JasperReport jasperReport = JasperCompileManager.compileReport(template.getJasperDesign());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, this.getProperties(gridReportConfiguration), ds);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
            exporter.setConfiguration(getPdfReportConfiguration());
            exporter.setConfiguration(getPdfExporterConfiguration());

            exporter.exportReport();
        } catch (JRException ex) {
            log.info("Error exporting to PDF: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private SimplePdfReportConfiguration getPdfReportConfiguration() {
        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);
        return reportConfig;
    }

    private SimplePdfExporterConfiguration getPdfExporterConfiguration() {
        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");
        return exportConfig;
    }

    private Map<String, Object> getProperties(GridReportConfiguration gridReportConfiguration) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(BaseReportTemplateBuilder.TITLE_LOGO_PARAMETER, gridReportConfiguration.getHeaderConfiguration().getLogoPath());
        return parameters;
    }
}
