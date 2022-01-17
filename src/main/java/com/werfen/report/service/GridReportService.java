package com.werfen.report.service;

import com.werfen.report.model.*;
import com.werfen.report.service.template.BaseReportTemplateBuilder;
import com.werfen.report.service.template.GridReportTemplateBuilder;
import net.sf.jasperreports.engine.JRException;

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
                PdfGridReportService pdfGridReportService = new PdfGridReportService();
                pdfGridReportService.exportToPdf(filePath, template.getJasperDesign(), this.getProperties(gridReportConfiguration), new GridReportDataSource(gridPageDataSource));
                break;
            case EXCEL:
                ExcelGridReportService excelGridReportService = new ExcelGridReportService();
                excelGridReportService.export(filePath, gridReportConfiguration.getHeaderConfiguration().getTitle(), gridReportConfiguration, gridPageDataSource);
                break;
            default:
                throw new RuntimeException("Report Format " + reportFormat + " is not currently supported");

        }
        return new File(filePath);
    }

    private Map<String, Object> getProperties(GridReportConfiguration gridReportConfiguration) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(BaseReportTemplateBuilder.TITLE_LOGO_PARAMETER, gridReportConfiguration.getHeaderConfiguration().getLogoPath());
        return parameters;
    }

}
