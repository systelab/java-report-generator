package com.werfen.report.service;

import com.werfen.report.model.*;
import com.werfen.report.service.template.BaseReportTemplateBuilder;
import com.werfen.report.service.template.XlsxSummaryReportTemplateBuilder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class XlsxService {


    Logger log = Logger.getLogger(GridReportService.class.getName());

    public File build(GridReportConfiguration gridReportConfiguration, GridReportData gridReportData, ReportFormat reportFormat, PageFormat pageFormat) throws JRException {

        String filePath = gridReportConfiguration.getOutputFilePath() + reportFormat.getFileExtension();
        XlsxSummaryReportTemplateBuilder template = new XlsxSummaryReportTemplateBuilder();

        template.initJasperDesign("excelReport", pageFormat);
        template.addHeader(gridReportConfiguration.getHeaderConfiguration());
        template.addSummary(gridReportConfiguration.getHeaderConfiguration(), gridReportConfiguration.getFooterConfiguration());
//        template.addGrid(gridReportConfiguration.getGridColumnConfigurations());
        String DATASHEET_TITLE = "Data";
        switch (reportFormat) {
            case EXCEL:
                this.exportToXlsx(filePath, new String[]{gridReportConfiguration.getHeaderConfiguration().getTitle(), DATASHEET_TITLE}, template.getJasperDesign(), this.getProperties(gridReportConfiguration), new GridReportDataSource(gridReportData));
                break;
            default:
                throw new RuntimeException("Report Format " + reportFormat + " is not currently supported");
        }
        return new File(filePath);
    }

    public void exportToXlsx(String filePath, String[] sheetNames, JasperDesign jasperDesign, Map<String, Object> parameters, JRDataSource ds) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
        exporter.setConfiguration(this.getXlsxReportConfiguration(sheetNames));

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

    private SimpleXlsxReportConfiguration getXlsxReportConfiguration(String[] sheetNames) {
        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
//        reportConfig.setOnePagePerSheet(true);
        reportConfig.setCellLocked(true);
        reportConfig.setWhitePageBackground(true);
//        reportConfig.setRemoveEmptySpaceBetweenColumns(true);
//        reportConfig.setColumnWidthRatio((float) (1 / 12));
//        reportConfig.setCollapseRowSpan(true);
        reportConfig.setSheetNames(sheetNames);

//        reportConfig.setCellHidden()
//        reportConfig.setMaxRowsPerSheet(50);
//        reportConfig.setFitWidth(1080);
        return reportConfig;
    }

}
