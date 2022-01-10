package com.werfen.report.service;

import com.werfen.report.model.*;
import com.werfen.report.service.template.BaseReportTemplateBuilder;
import com.werfen.report.service.template.GridReportTemplateBuilder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GridReportService {

    Logger log = Logger.getLogger(GridReportService.class.getName());

    public File build(GridReportConfiguration gridReportConfiguration, GridReportData gridReportData, ReportFormat reportFormat, PageFormat pageFormat) throws JRException {

        String filePath = gridReportConfiguration.getOutputFilePath() + reportFormat.getFileExtension();
        GridReportTemplateBuilder template = new GridReportTemplateBuilder();

        template.initJasperDesign("gridReport", pageFormat);
        template.addHeader(gridReportConfiguration.getHeaderConfiguration());
        template.addFooter(gridReportConfiguration.getFooterConfiguration());
        template.addGrid(gridReportConfiguration.getGridColumnConfigurations());
        switch (reportFormat) {
            case PDF:
                this.exportToPdf(filePath, template.getJasperDesign(), this.getProperties(gridReportConfiguration), new GridReportDataSource(gridReportData));
                break;
            case EXCEL:
                this.exportToXlsx(filePath, gridReportConfiguration.getHeaderConfiguration().getTitle(), template.getJasperDesign(), this.getProperties(gridReportConfiguration), new GridReportDataSource(gridReportData));
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

    public void exportToXlsx(String filePath, String sheetName, JasperDesign jasperDesign, Map<String, Object> parameters, JRDataSource ds) throws JRException {
        JasperReport jasperReport  = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,  parameters, ds);

        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
        exporter.setConfiguration(this.getXlsxReportConfiguration(sheetName));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.info("Error generating xls : " + ex.getMessage());
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

    private SimpleXlsxReportConfiguration getXlsxReportConfiguration(String sheetName) {
        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setOnePagePerSheet(true);
        reportConfig.setWhitePageBackground(false);
        reportConfig.setRemoveEmptySpaceBetweenColumns(true);
        reportConfig.setColumnWidthRatio((float) (1 / 12));
        reportConfig.setCollapseRowSpan(true);
        reportConfig.setSheetNames(new String[] { sheetName });
        return reportConfig;
    }

}
