package com.werfen.report.service.pdf;

import com.werfen.report.model.FormReportConfiguration;
import com.werfen.report.model.FormReportData;
import com.werfen.report.model.PageFormat;
import com.werfen.report.service.pdf.template.FooterReportTemplateBuilder;
import com.werfen.report.service.pdf.template.FormReportTemplateBuilder;
import com.werfen.report.service.pdf.template.HeaderReportTemplateBuilder;
import com.werfen.report.service.pdf.template.JasperDesignFactory;
import com.werfen.report.service.pdf.util.PdfExportService;
import com.werfen.report.service.pdf.util.PdfFillService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.io.File;

public class PdfFormReportService {

    public File export(FormReportConfiguration formReportConfiguration, FormReportData formReportData, PageFormat pageFormat) throws JRException {

        JasperDesign jasperDesign = new JasperDesignFactory().build("formReport", pageFormat);

        new HeaderReportTemplateBuilder().addHeader(jasperDesign, formReportConfiguration.getHeaderConfiguration());
        new FormReportTemplateBuilder().addForm(jasperDesign, formReportData);
        new FooterReportTemplateBuilder().addFooter(jasperDesign, formReportConfiguration.getFooterConfiguration());

        JasperPrint jasperPrint= new PdfFillService().fill(jasperDesign,  new JREmptyDataSource(), formReportConfiguration.getHeaderConfiguration());

        return new PdfExportService().export(jasperPrint, formReportConfiguration.getOutputFilePath());
    }
}
