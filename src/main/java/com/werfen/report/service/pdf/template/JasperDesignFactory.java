package com.werfen.report.service.pdf.template;

import com.werfen.report.model.PageFormat;
import com.werfen.report.model.ReportFooterConfiguration;
import com.werfen.report.model.ReportHeaderConfiguration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalImageAlignEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

import static java.util.Objects.isNull;

public class JasperDesignFactory {

    public static final String TITLE_LOGO_PARAMETER = "TITLE_LOGO_PATH";
    protected static final int PAGE_MARGIN = 20;
    private static final String STYLE_NAME = "default";
    private static final String STYLE_ENCODING = "UTF-8";
    private static final Float STYLE_FONT_SIZE = 7f;

    public JasperDesign build(String name, PageFormat pageFormat) {
        if (isNull(pageFormat)) {
            pageFormat = PageFormat.A4;
        }

        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName(name);
        jasperDesign.setPageWidth(pageFormat.getWidth());
        jasperDesign.setPageHeight(pageFormat.getHeight());
        jasperDesign.setBottomMargin(PAGE_MARGIN);
        jasperDesign.setTopMargin(PAGE_MARGIN);
        jasperDesign.setLeftMargin(PAGE_MARGIN);
        jasperDesign.setRightMargin(PAGE_MARGIN);

        setDefaultStyle(jasperDesign);

        return jasperDesign;
    }

    public void addHeader(JasperDesign jasperDesign, ReportHeaderConfiguration reportHeaderConfiguration) throws JRException {
        HeaderReportTemplateBuilder builder = new HeaderReportTemplateBuilder();
        builder.addHeader(jasperDesign, reportHeaderConfiguration);
    }

    public void addFooter(JasperDesign jasperDesign, ReportFooterConfiguration reportFooterConfiguration) {
        FooterReportTemplateBuilder builder = new FooterReportTemplateBuilder();
        builder.addFooter(jasperDesign, reportFooterConfiguration);
    }

    private void setDefaultStyle(JasperDesign jasperDesign) {
        JRDesignStyle defaultStyle = new JRDesignStyle();
        defaultStyle.setDefault(true);
        defaultStyle.setName(STYLE_NAME);
        defaultStyle.setFontSize(STYLE_FONT_SIZE);
        defaultStyle.setPdfEncoding(STYLE_ENCODING);
        defaultStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        defaultStyle.setHorizontalImageAlign(HorizontalImageAlignEnum.CENTER);

        jasperDesign.setDefaultStyle(defaultStyle);
    }
}
