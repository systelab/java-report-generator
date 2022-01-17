package com.werfen.report.service;

import com.werfen.report.model.*;
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

    public File build(GridReportConfiguration gridReportConfiguration, GridPageDataSource gridPageDataSource, ReportFormat reportFormat, PageFormat pageFormat) throws JRException {

        String filePath = gridReportConfiguration.getOutputFilePath() + reportFormat.getFileExtension();
        GridReportTemplateBuilder template = new GridReportTemplateBuilder();
        template.initJasperDesign("gridReport", pageFormat);
        template.addHeader(gridReportConfiguration.getHeaderConfiguration());
        template.addFooter(gridReportConfiguration.getFooterConfiguration());
        template.addGrid(gridReportConfiguration.getGridColumnConfigurations());
        switch (reportFormat) {
            case PDF:
                this.exportToPdf(filePath, template.getJasperDesign(), this.getProperties(gridReportConfiguration), new GridReportDataSource(gridPageDataSource));
                break;
            case EXCEL:
                ExcelGridReportService excelGridReportService =  new ExcelGridReportService();
                excelGridReportService.export(filePath, gridReportConfiguration.getHeaderConfiguration().getTitle(), gridReportConfiguration,gridPageDataSource);
                break;
            default:
                throw new RuntimeException("Report Format " + reportFormat + " is not currently supported");

        }
        return new File(filePath);
    }

    private void exportToPdf(String filePath, JasperDesign jasperDesign, Map<String, Object> parameters, JRDataSource ds) throws JRException {

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
        exporter.setConfiguration(getPdfReportConfiguration());
        exporter.setConfiguration(getPdfExporterConfiguration());

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.info("Error exporting to PDF: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



    private Map<String, Object> getProperties(GridReportConfiguration gridReportConfiguration) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(BaseReportTemplateBuilder.TITLE_LOGO_PARAMETER, gridReportConfiguration.getHeaderConfiguration().getLogoPath());
        return parameters;
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
}
