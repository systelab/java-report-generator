package com.werfen.report.service.pdf.util;

import com.werfen.report.model.ReportHeaderConfiguration;
import com.werfen.report.service.pdf.template.JasperDesignFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.HashMap;
import java.util.Map;

public class PdfFillService {

    public JasperPrint fill(JasperDesign jasperDesign, JRDataSource dataSource, ReportHeaderConfiguration configuration) throws JRException{
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        return  JasperFillManager.fillReport(jasperReport, this.getParameters(configuration), dataSource);

    }

    private Map<String, Object> getParameters(ReportHeaderConfiguration configuration) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(JasperDesignFactory.TITLE_LOGO_PARAMETER, configuration.getLogoPath());
        return parameters;
    }

}
