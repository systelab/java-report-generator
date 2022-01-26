package com.werfen.report.service.pdf;

import com.werfen.report.model.FormReportConfiguration;
import com.werfen.report.model.FormReportData;
import com.werfen.report.model.PageFormat;
import com.werfen.report.service.pdf.template.BaseReportTemplateBuilder;
import com.werfen.report.service.pdf.template.FormReportTemplateBuilder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import java.util.HashMap;
import java.util.Map;

public class PdfFormReportService {


    public void export(String filePath, FormReportConfiguration formReportConfiguration, FormReportData formReportData, PageFormat pageFormat) throws JRException {

        FormReportTemplateBuilder template = new FormReportTemplateBuilder();
        template.initJasperDesign("formReport", pageFormat);
        template.addHeader(formReportConfiguration.getHeaderConfiguration());
        template.addFooter(formReportConfiguration.getFooterConfiguration());
        template.addForm(formReportData);

        JasperReport jasperReport = JasperCompileManager.compileReport(template.getJasperDesign());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, getProperties(formReportConfiguration), new JREmptyDataSource());

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
        exporter.setConfiguration(getBreakConfiguration());
        exporter.setConfiguration(getSecurityConfiguration());

        exporter.exportReport();
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
