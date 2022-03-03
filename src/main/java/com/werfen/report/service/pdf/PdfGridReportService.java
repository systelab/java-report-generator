package com.werfen.report.service.pdf;

import com.werfen.report.model.GridPageDataSource;
import com.werfen.report.model.GridReportConfiguration;
import com.werfen.report.model.GridReportDataSource;
import com.werfen.report.model.PageFormat;
import com.werfen.report.service.pdf.template.FooterReportTemplateBuilder;
import com.werfen.report.service.pdf.template.GridReportTemplateBuilder;
import com.werfen.report.service.pdf.template.HeaderReportTemplateBuilder;
import com.werfen.report.service.pdf.template.JasperDesignFactory;
import com.werfen.report.service.pdf.util.PdfExportService;
import com.werfen.report.service.pdf.util.PdfFillService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;

public class PdfGridReportService {

    public void export(String filePath, GridReportConfiguration gridReportConfiguration, GridPageDataSource gridPageDataSource, PageFormat pageFormat) throws JRException {
        JasperDesign jasperDesign = new JasperDesignFactory().build("gridReport", pageFormat);

        new HeaderReportTemplateBuilder().addHeader(jasperDesign, gridReportConfiguration.getHeaderConfiguration());
        new GridReportTemplateBuilder().addGrid(jasperDesign, gridReportConfiguration.getGridColumnConfigurations());
        new FooterReportTemplateBuilder().addFooter(jasperDesign, gridReportConfiguration.getFooterConfiguration());

        JasperPrint jasperPrint= new PdfFillService().fill(jasperDesign, new GridReportDataSource(gridPageDataSource), gridReportConfiguration.getHeaderConfiguration());

        new PdfExportService().export(jasperPrint, filePath);

    }




}
