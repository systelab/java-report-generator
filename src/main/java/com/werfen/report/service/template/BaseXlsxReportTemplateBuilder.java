package com.werfen.report.service.template;

import com.werfen.report.model.PageFormat;
import com.werfen.report.model.ReportField;
import com.werfen.report.model.ReportFooterConfiguration;
import com.werfen.report.model.ReportHeaderConfiguration;
import com.werfen.report.util.DesignImageBuilder;
import com.werfen.report.util.DesignLineBuilder;
import com.werfen.report.util.DesignRectangleBuilder;
import com.werfen.report.util.DesignTextBuilder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.*;

import java.awt.*;

import static java.util.Objects.nonNull;

public class BaseXlsxReportTemplateBuilder {

    public static final String TITLE_LOGO_PARAMETER = "TITLE_LOGO_PATH";
    private static final int TITLE_HEIGHT = 42;
    private static final int TITLE_X = 10;
    private static final int TITLE_Y = 12;
    private static final int TITLE_LOGO_WIDTH = 81;
    private static final int TITLE_LOGO_HEIGHT = 26;
    private static final int TITLE_LOGO_X = 465;
    private static final int TITLE_LOGO_Y = 9;
    private static final int TITLE_TEXT_WIDTH = 116;
    private static final int TITLE_TEXT_HEIGHT = 18;
    private static final int TITLE_FRAME_WIDTH = 556;
    private static final int TITLE_FRAME_HEIGHT = 42;
    private static final int TITLE_FRAME_X = 0;
    private static final int TITLE_FRAME_Y = 0;
    private static final int TITLE_LINE_SEPARATOR_WIDTH = 1;
    private static final int TITLE_LINE_SEPARATOR_HEIGHT = 42;
    private static final int TITLE_LINE_SEPARATOR_X = 457;
    private static final int TITLE_LINE_SEPARATOR_Y = 0;
    private static final Float TITLE_TEXT_FONT_SIZE = 14f;

    private static final int HEADER_HEIGHT = 48;
    private static final int HEADER_FIELDS_WIDTH = 139;
    private static final int HEADER_FIELDS_HEIGHT = 11;
    private static final int HEADER_FIELDS_TITLE_Y = 7;
    private static final int HEADER_FIELDS_Y = 21;
    private static final int HEADER_FIELD_1_X = 10;
    private static final int HEADER_FIELD_2_X = 144;
    private static final int HEADER_FIELD_3_X = 278;
    private static final int HEADER_FIELD_4_X = 412;
    private static final int HEADER_FRAME_WIDTH = 556;
    private static final int HEADER_FRAME_HEIGHT = 38;
    private static final int HEADER_FRAME_X = 0;
    private static final int HEADER_FRAME_Y = 0;

    private static final String FOOTER_CREATED_AT_TITLE_TEXT = "Created at:";
    private static final int FOOTER_CREATED_AT_WIDTH = 85;
    private static final int FOOTER_TEXT_FIELD_HEIGHT = 9;
    private static final int FOOTER_CREATED_AT_X = 11;
    private static final int FOOTER_TITLE_TEXT_FIELD_Y = 8;
    private static final int FOOTER_TEXT_FIELD_Y = 17;
    private static final int FOOTER_ADDITIONAL_FIELD_1_WIDTH = 80;
    private static final int FOOTER_ADDITIONAL_FIELD_1_X = 123;
    private static final int FOOTER_ADDITIONAL_FIELD_2_WIDTH = 80;
    private static final int FOOTER_ADDITIONAL_FIELD_2_X = 243;
    private static final int FOOTER_PAGE_NUMBER_TEXT_WIDTH = 32;
    private static final int FOOTER_PAGE_NUMBER_TEXT_X = 510;
    private static final int FOOTER_PAGE_NUMBER_TEXT_Y = 13;
    private static final int FOOTER_FRAME_WIDTH = 556;
    private static final int FOOTER_FRAME_HEIGHT = 34;
    private static final int FOOTER_FRAME_X = 0;
    private static final int FOOTER_FRAME_Y = 0;
    private static final int FOOTER_HEIGHT = 34;

    private static final int PAGE_MARGIN = 0;

    private static final String STYLE_NAME = "default";
    private static final String STYLE_ENCODING = "UTF-8";
    private static final Float STYLE_FONT_SIZE = 7f;

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
        this.jasperDesign.setPageWidth(pageFormat.getWidth());
        this.jasperDesign.setPageHeight(pageFormat.getHeight());
        this.jasperDesign.setBottomMargin(PAGE_MARGIN);
        this.jasperDesign.setTopMargin(PAGE_MARGIN);
        this.jasperDesign.setLeftMargin(PAGE_MARGIN);
        this.jasperDesign.setRightMargin(PAGE_MARGIN);

        setDefaultStyle();
    }

    public void addHeader(ReportHeaderConfiguration reportHeaderConfiguration) throws JRException {

        JRDesignRectangle titleFrame = new DesignRectangleBuilder()
                .position(TITLE_FRAME_X, TITLE_FRAME_Y, TITLE_FRAME_WIDTH, TITLE_FRAME_HEIGHT).build();

        JRDesignLine titleFrameSeparator = new DesignLineBuilder()
                .position(TITLE_LINE_SEPARATOR_X, TITLE_LINE_SEPARATOR_Y, TITLE_LINE_SEPARATOR_WIDTH, TITLE_LINE_SEPARATOR_HEIGHT).build();

        JRDesignStaticText titleText = new DesignTextBuilder()
                .text(reportHeaderConfiguration.getTitle())
                .position(TITLE_X, TITLE_Y, TITLE_TEXT_WIDTH, TITLE_TEXT_HEIGHT)
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
                .position(TITLE_LOGO_X, TITLE_LOGO_Y, TITLE_LOGO_WIDTH, TITLE_LOGO_HEIGHT).build();

        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(TITLE_HEIGHT);
        titleBand.setSplitType(SplitTypeEnum.STRETCH);
        titleBand.addElement(titleFrame);
        titleBand.addElement(titleFrameSeparator);
        titleBand.addElement(titleText);
        titleBand.addElement(titleLogoImage);

        this.jasperDesign.addParameter(titleLogoParameter);
        this.jasperDesign.setTitle(titleBand);

        JRDesignBand headerBand = new JRDesignBand();
        headerBand.setHeight(HEADER_HEIGHT);
        headerBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignRectangle headerFrame = new DesignRectangleBuilder()
                .position(HEADER_FRAME_X, HEADER_FRAME_Y, HEADER_FRAME_WIDTH, HEADER_FRAME_HEIGHT).build();

        headerBand.addElement(headerFrame);

        if (nonNull(reportHeaderConfiguration.getField1())) {
            addField(headerBand, reportHeaderConfiguration.getField1(), HEADER_FIELD_1_X);
        }

        if (nonNull(reportHeaderConfiguration.getField2())) {
            addField(headerBand, reportHeaderConfiguration.getField2(), HEADER_FIELD_2_X);
        }

        if (nonNull(reportHeaderConfiguration.getField3())) {
            addField(headerBand, reportHeaderConfiguration.getField3(), HEADER_FIELD_3_X);
        }

        if (nonNull(reportHeaderConfiguration.getField2())) {
            addField(headerBand, reportHeaderConfiguration.getField4(), HEADER_FIELD_4_X);
        }
        this.jasperDesign.setPageHeader(headerBand);
    }

    private void addField(JRDesignBand headerBand, ReportField field, int xPos) {
        JRDesignStaticText headerField1TitleText = new DesignTextBuilder()
                .text(field.getName())
                .position(xPos, HEADER_FIELDS_TITLE_Y, HEADER_FIELDS_WIDTH, HEADER_FIELDS_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        JRDesignStaticText headerField1Text = new DesignTextBuilder()
                .text(field.getValue())
                .position(xPos, HEADER_FIELDS_Y, HEADER_FIELDS_WIDTH, HEADER_FIELDS_HEIGHT)
                .font("Helvetica-Bold")
                .bold()
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        headerBand.addElement(headerField1TitleText);
        headerBand.addElement(headerField1Text);
    }

    public void addFooter(ReportFooterConfiguration reportFooterConfiguration) {

        JRDesignBand footerBand = new JRDesignBand();
        footerBand.setHeight(FOOTER_HEIGHT);
        footerBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignRectangle footerFrame = new DesignRectangleBuilder()
                .position(FOOTER_FRAME_X, FOOTER_FRAME_Y, FOOTER_FRAME_WIDTH, FOOTER_FRAME_HEIGHT).build();

        footerBand.addElement(footerFrame);

        JRDesignStaticText footerCreatedAtTitleText = new DesignTextBuilder()
                .text(FOOTER_CREATED_AT_TITLE_TEXT)
                .position(FOOTER_CREATED_AT_X, FOOTER_TITLE_TEXT_FIELD_Y, FOOTER_CREATED_AT_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        JRDesignStaticText footerCreatedAtText = new DesignTextBuilder()
                .text(reportFooterConfiguration.getCreatedAt())
                .position(FOOTER_CREATED_AT_X, FOOTER_TEXT_FIELD_Y, FOOTER_CREATED_AT_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                .font("Helvetica-Bold")
                .bold()
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        footerBand.addElement(footerCreatedAtTitleText);
        footerBand.addElement(footerCreatedAtText);

        if (nonNull(reportFooterConfiguration.getAdditionalField1())) {

            JRDesignStaticText footerAdditionalField1TitleText = new DesignTextBuilder()
                    .text(reportFooterConfiguration.getAdditionalField1().getName())
                    .position(FOOTER_ADDITIONAL_FIELD_1_X, FOOTER_TITLE_TEXT_FIELD_Y, FOOTER_ADDITIONAL_FIELD_1_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                    .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                    .buildStaticText();

            JRDesignStaticText footerAdditionalField1Text = new DesignTextBuilder()
                    .text(reportFooterConfiguration.getAdditionalField1().getValue())
                    .position(FOOTER_ADDITIONAL_FIELD_1_X, FOOTER_TEXT_FIELD_Y, FOOTER_ADDITIONAL_FIELD_1_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                    .font("Helvetica-Bold")
                    .bold()
                    .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                    .buildStaticText();

            footerBand.addElement(footerAdditionalField1TitleText);
            footerBand.addElement(footerAdditionalField1Text);
        }

        if (nonNull(reportFooterConfiguration.getAdditionalField2())) {

            JRDesignStaticText footerAdditionalField2TitleText = new DesignTextBuilder()
                    .text(reportFooterConfiguration.getAdditionalField2().getName())
                    .position(FOOTER_ADDITIONAL_FIELD_2_X, FOOTER_TITLE_TEXT_FIELD_Y, FOOTER_ADDITIONAL_FIELD_2_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                    .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                    .buildStaticText();

            JRDesignStaticText footerAdditionalField2Text = new DesignTextBuilder()
                    .text(reportFooterConfiguration.getAdditionalField2().getValue())
                    .position(FOOTER_ADDITIONAL_FIELD_2_X, FOOTER_TEXT_FIELD_Y, FOOTER_ADDITIONAL_FIELD_2_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                    .font("Helvetica-Bold")
                    .bold()
                    .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                    .buildStaticText();

            footerBand.addElement(footerAdditionalField2TitleText);
            footerBand.addElement(footerAdditionalField2Text);
        }
        JRDesignExpression masterCurrentPageExpression = new JRDesignExpression();
        masterCurrentPageExpression.setText("$V{MASTER_CURRENT_PAGE}");

        JRDesignVariable masterCurrentPageVariable = new JRDesignVariable();
        masterCurrentPageVariable.setExpression(masterCurrentPageExpression);

        JRDesignExpression masterTotalPagesExpression = new JRDesignExpression();
        masterTotalPagesExpression.setText("$V{MASTER_TOTAL_PAGES}");

        JRDesignVariable masterTotalPagesVariable = new JRDesignVariable();
        masterTotalPagesVariable.setExpression(masterTotalPagesExpression);

        JRDesignExpression pageNumberExpression = new JRDesignExpression();
        pageNumberExpression.setText("Page $V{MASTER_CURRENT_PAGE} / $V{MASTER_TOTAL_PAGES}");
        pageNumberExpression.setType(ExpressionTypeEnum.SIMPLE_TEXT);


        JRDesignTextField pageNumberText = new DesignTextBuilder()
                .expression(pageNumberExpression)
                .evaluationTime(EvaluationTimeEnum.MASTER)
                .position(FOOTER_PAGE_NUMBER_TEXT_X, FOOTER_PAGE_NUMBER_TEXT_Y, FOOTER_PAGE_NUMBER_TEXT_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildTextField();

        footerBand.addElement(pageNumberText);

        this.jasperDesign.setPageFooter(footerBand);
    }
}
