package com.werfen.report.service.template;

import com.werfen.report.model.GridReportField;
import com.werfen.report.model.PageFormat;
import com.werfen.report.model.ReportFooterConfiguration;
import com.werfen.report.model.ReportHeaderConfiguration;
import com.werfen.report.util.DesignImageBuilder;
import com.werfen.report.util.DesignTextBuilder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalImageAlignEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

import static java.util.Objects.nonNull;

public class XlsxSummaryReportTemplateBuilder {
    public static final String TITLE_LOGO_PARAMETER = "TITLE_LOGO_PATH";
    private static final String BOLD_FONT = "Helvetica-Bold";
    private static final String STYLE_NAME = "default";
    private static final Float STYLE_FONT_SIZE = 14f;
    private static final Float TITLE_TEXT_FONT_SIZE = 28f;
    private static final int TITLE_HEIGHT = 100;
    private static final int TITLE_LOGO_HEIGHT = 50;

    private static final int SUMMARY_FIELDS_HEIGHT = 25;
    private static final int SUMMARY_FIELDS_Y = 0;
    private static final int SUMMARY_HEIGHT = 200;


    protected JasperDesign jasperDesign;

    private void setDefaultStyle() {
        JRDesignStyle defaultStyle = new JRDesignStyle();
        defaultStyle.setDefault(true);
        defaultStyle.setName(STYLE_NAME);
        defaultStyle.setFontSize(STYLE_FONT_SIZE);
        defaultStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        defaultStyle.setHorizontalImageAlign(HorizontalImageAlignEnum.CENTER);

        jasperDesign.setDefaultStyle(defaultStyle);
    }

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    public void initJasperDesign(String name, PageFormat pageFormat) {
        this.jasperDesign = new JasperDesign();

        this.jasperDesign.setName(name);
        this.jasperDesign.setColumnWidth(pageFormat.getWidth() / 2);
        this.jasperDesign.setColumnCount(2);
        this.jasperDesign.setPageWidth(pageFormat.getWidth());
        this.jasperDesign.setPageHeight(pageFormat.getHeight());
        this.jasperDesign.setBottomMargin(0);
        this.jasperDesign.setTopMargin(0);
        this.jasperDesign.setLeftMargin(0);
        this.jasperDesign.setRightMargin(0);

        setDefaultStyle();
    }


    public void addHeader(ReportHeaderConfiguration reportHeaderConfiguration) throws JRException {
        final int BAND_WIDTH = this.jasperDesign.getPageWidth();
        final int TITLE_LOGO_WIDTH = BAND_WIDTH / 2;

        JRDesignStaticText titleText = new DesignTextBuilder()
                .text(reportHeaderConfiguration.getTitle())
                .position(0, 0, TITLE_LOGO_WIDTH, TITLE_LOGO_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .fontSize(TITLE_TEXT_FONT_SIZE)
                .buildStaticText();

        JRDesignParameter titleLogoParameter = new JRDesignParameter();
        titleLogoParameter.setName(TITLE_LOGO_PARAMETER);
        titleLogoParameter.setValueClass(String.class);

        JRDesignExpression titleLogoExpression = new JRDesignExpression();
        titleLogoExpression.setText("$P{" + TITLE_LOGO_PARAMETER + "}");

        JRDesignImage titleLogoImage = new DesignImageBuilder(this.jasperDesign)
                .expression(titleLogoExpression)
                .position(TITLE_LOGO_WIDTH, 0, TITLE_LOGO_WIDTH, TITLE_LOGO_HEIGHT).build();
        titleLogoImage.setHorizontalImageAlign(HorizontalImageAlignEnum.RIGHT);

        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(TITLE_HEIGHT);
        titleBand.addElement(titleText);
        titleBand.addElement(titleLogoImage);

        this.jasperDesign.addParameter(titleLogoParameter);
        this.jasperDesign.setTitle(titleBand);
    }

    public void addSummary(ReportHeaderConfiguration reportHeaderConfiguration, ReportFooterConfiguration reportFooterConfiguration) {
        final int SUMMARY_FIELDS_WIDTH = this.jasperDesign.getPageWidth() / 2;
        JRDesignBand summaryBand = new JRDesignBand();
        summaryBand.setHeight(SUMMARY_HEIGHT);

        if (nonNull(reportHeaderConfiguration.getField1())) {
            addField(summaryBand, reportHeaderConfiguration.getField1(), SUMMARY_FIELDS_Y, SUMMARY_FIELDS_WIDTH);
        }

        if (nonNull(reportHeaderConfiguration.getField2())) {
            addField(summaryBand, reportHeaderConfiguration.getField2(), SUMMARY_FIELDS_Y + (SUMMARY_FIELDS_HEIGHT), SUMMARY_FIELDS_WIDTH);
        }

        if (nonNull(reportHeaderConfiguration.getField3())) {
            addField(summaryBand, reportHeaderConfiguration.getField3(), SUMMARY_FIELDS_Y + (SUMMARY_FIELDS_HEIGHT * 2), SUMMARY_FIELDS_WIDTH);
        }

        if (nonNull(reportHeaderConfiguration.getField2())) {
            addField(summaryBand, reportHeaderConfiguration.getField4(), SUMMARY_FIELDS_Y + (SUMMARY_FIELDS_HEIGHT * 3), SUMMARY_FIELDS_WIDTH);
        }

        if (nonNull(reportFooterConfiguration.getField1())) {
            addField(summaryBand, reportFooterConfiguration.getField1(), SUMMARY_FIELDS_Y + (SUMMARY_FIELDS_HEIGHT * 5), SUMMARY_FIELDS_WIDTH);
        }

        if (nonNull(reportFooterConfiguration.getField2())) {
            addField(summaryBand, reportFooterConfiguration.getField2(), SUMMARY_FIELDS_Y + (SUMMARY_FIELDS_HEIGHT * 6), SUMMARY_FIELDS_WIDTH);
        }

        if (nonNull(reportFooterConfiguration.getField3())) {
            addField(summaryBand, reportFooterConfiguration.getField3(), SUMMARY_FIELDS_Y + (SUMMARY_FIELDS_HEIGHT * 7), SUMMARY_FIELDS_WIDTH);
        }

        this.jasperDesign.setSummary(summaryBand);
    }

    private void addField(JRDesignBand band, GridReportField field, int yFieldPos, int fieldWidth) {
        JRDesignStaticText headerField1TitleText = new DesignTextBuilder()
                .text(field.getName())
                .position(0, yFieldPos, fieldWidth, XlsxSummaryReportTemplateBuilder.SUMMARY_FIELDS_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.RIGHT)
                .buildStaticText();

        JRDesignStaticText headerField1Text = new DesignTextBuilder()
                .text(field.getValue())
                .position(fieldWidth, yFieldPos, fieldWidth, XlsxSummaryReportTemplateBuilder.SUMMARY_FIELDS_HEIGHT)
                .font(BOLD_FONT)
                .bold()
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        band.addElement(headerField1TitleText);
        band.addElement(headerField1Text);
    }
}
