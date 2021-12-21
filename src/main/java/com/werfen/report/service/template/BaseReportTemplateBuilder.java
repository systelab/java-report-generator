package com.werfen.report.service.template;

import com.werfen.report.model.PageFormat;
import com.werfen.report.model.ReportField;
import com.werfen.report.model.ReportFooterConfiguration;
import com.werfen.report.model.ReportHeaderConfiguration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.*;

import static java.util.Objects.nonNull;

public class BaseReportTemplateBuilder {

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
    private static final int HEADER_X = 10;
    private static final int HEADER_Y = 12;
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

    private static final int PAGE_MARGIN = 20;

    private static final String STYLE_NAME = "default";
    private static final String STYLE_FONT_NAME = "Helvetica";
    private static final String STYLE_ENCODING = "UTF-8";
    private static final Float STYLE_FONT_SIZE = 7f;

    protected String reportName;
    protected PageFormat pageFormat;
    protected JasperDesign jasperDesign;

    private void setDefaultStyle() {
        JRDesignStyle defaultStyle = new JRDesignStyle();
        defaultStyle.setDefault(true);
        defaultStyle.setName(STYLE_NAME);
        defaultStyle.setFontSize(STYLE_FONT_SIZE);
        defaultStyle.setPdfEncoding(STYLE_ENCODING);
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
        JRDesignRectangle titleFrame = new JRDesignRectangle();
        titleFrame.setWidth(TITLE_FRAME_WIDTH);
        titleFrame.setHeight(TITLE_FRAME_HEIGHT);
        titleFrame.setX(TITLE_FRAME_X);
        titleFrame.setY(TITLE_FRAME_Y);

        JRDesignLine titleFrameSeparator = new JRDesignLine();
        titleFrameSeparator.setWidth(TITLE_LINE_SEPARATOR_WIDTH);
        titleFrameSeparator.setHeight(TITLE_LINE_SEPARATOR_HEIGHT);
        titleFrameSeparator.setX(TITLE_LINE_SEPARATOR_X);
        titleFrameSeparator.setY(TITLE_LINE_SEPARATOR_Y);

        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setFontSize(TITLE_TEXT_FONT_SIZE);
        titleText.setWidth(TITLE_TEXT_WIDTH);
        titleText.setHeight(TITLE_TEXT_HEIGHT);
        titleText.setX(TITLE_X);
        titleText.setY(TITLE_Y);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        titleText.setText(reportHeaderConfiguration.getTitle());

        JRDesignParameter titleLogoParameter = new JRDesignParameter();
        titleLogoParameter.setName(TITLE_LOGO_PARAMETER);
        titleLogoParameter.setValueClass(String.class);

        JRDesignExpression titleLogoExpression = new JRDesignExpression();
        titleLogoExpression.setText("$P{" + TITLE_LOGO_PARAMETER + "}");

        JRDesignImage titleLogoImage = new JRDesignImage(this.jasperDesign);
        titleLogoImage.setWidth(TITLE_LOGO_WIDTH);
        titleLogoImage.setHeight(TITLE_LOGO_HEIGHT);
        titleLogoImage.setX(TITLE_LOGO_X);
        titleLogoImage.setY(TITLE_LOGO_Y);
        titleLogoImage.setExpression(titleLogoExpression);

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

        JRDesignRectangle headerFrame = new JRDesignRectangle();
        headerFrame.setWidth(HEADER_FRAME_WIDTH);
        headerFrame.setHeight(HEADER_FRAME_HEIGHT);
        headerFrame.setX(HEADER_FRAME_X);
        headerFrame.setY(HEADER_FRAME_Y);

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
        JRDesignStaticText headerField1TitleText = new JRDesignStaticText();
        headerField1TitleText.setWidth(HEADER_FIELDS_WIDTH);
        headerField1TitleText.setHeight(HEADER_FIELDS_HEIGHT);
        headerField1TitleText.setX(xPos);
        headerField1TitleText.setY(HEADER_FIELDS_TITLE_Y);
        headerField1TitleText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        headerField1TitleText.setText(field.getName());

        JRDesignStaticText headerField1Text = new JRDesignStaticText();
        headerField1Text.setWidth(HEADER_FIELDS_WIDTH);
        headerField1Text.setHeight(HEADER_FIELDS_HEIGHT);
        headerField1Text.setX(xPos);
        headerField1Text.setY(HEADER_FIELDS_Y);
        headerField1Text.setPdfFontName("Helvetica-Bold");
        headerField1Text.setBold(Boolean.TRUE);
        headerField1Text.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        headerField1Text.setText(field.getValue());

        headerBand.addElement(headerField1TitleText);
        headerBand.addElement(headerField1Text);
    }

    public void addFooter(ReportFooterConfiguration reportFooterConfiguration) {

        JRDesignBand footerBand = new JRDesignBand();
        footerBand.setHeight(FOOTER_HEIGHT);
        footerBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignRectangle footerFrame = new JRDesignRectangle();
        footerFrame.setWidth(FOOTER_FRAME_WIDTH);
        footerFrame.setHeight(FOOTER_FRAME_HEIGHT);
        footerFrame.setX(FOOTER_FRAME_X);
        footerFrame.setY(FOOTER_FRAME_Y);

        footerBand.addElement(footerFrame);

        JRDesignStaticText footerCreatedAtTitleText = new JRDesignStaticText();
        footerCreatedAtTitleText.setWidth(FOOTER_CREATED_AT_WIDTH);
        footerCreatedAtTitleText.setHeight(FOOTER_TEXT_FIELD_HEIGHT);
        footerCreatedAtTitleText.setX(FOOTER_CREATED_AT_X);
        footerCreatedAtTitleText.setY(FOOTER_TITLE_TEXT_FIELD_Y);
        footerCreatedAtTitleText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        footerCreatedAtTitleText.setText(FOOTER_CREATED_AT_TITLE_TEXT);

        JRDesignStaticText footerCreatedAtText = new JRDesignStaticText();
        footerCreatedAtText.setWidth(FOOTER_CREATED_AT_WIDTH);
        footerCreatedAtText.setHeight(FOOTER_TEXT_FIELD_HEIGHT);
        footerCreatedAtText.setX(FOOTER_CREATED_AT_X);
        footerCreatedAtText.setY(FOOTER_TEXT_FIELD_Y);
        footerCreatedAtText.setPdfFontName("Helvetica-Bold");
        footerCreatedAtText.setBold(Boolean.TRUE);
        footerCreatedAtText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        footerCreatedAtText.setText(reportFooterConfiguration.getCreatedAt());

        footerBand.addElement(footerCreatedAtTitleText);
        footerBand.addElement(footerCreatedAtText);

        if (nonNull(reportFooterConfiguration.getAdditionalField1())) {
            JRDesignStaticText footerAdditionalField1TitleText = new JRDesignStaticText();
            footerAdditionalField1TitleText.setWidth(FOOTER_ADDITIONAL_FIELD_1_WIDTH);
            footerAdditionalField1TitleText.setHeight(FOOTER_TEXT_FIELD_HEIGHT);
            footerAdditionalField1TitleText.setX(FOOTER_ADDITIONAL_FIELD_1_X);
            footerAdditionalField1TitleText.setY(FOOTER_TITLE_TEXT_FIELD_Y);
            footerAdditionalField1TitleText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
            footerAdditionalField1TitleText.setText(reportFooterConfiguration.getAdditionalField1().getName());

            JRDesignStaticText footerAdditionalField1Text = new JRDesignStaticText();
            footerAdditionalField1Text.setWidth(FOOTER_ADDITIONAL_FIELD_1_WIDTH);
            footerAdditionalField1Text.setHeight(FOOTER_TEXT_FIELD_HEIGHT);
            footerAdditionalField1Text.setX(FOOTER_ADDITIONAL_FIELD_1_X);
            footerAdditionalField1Text.setY(FOOTER_TEXT_FIELD_Y);
            footerAdditionalField1Text.setPdfFontName("Helvetica-Bold");
            footerAdditionalField1Text.setBold(Boolean.TRUE);
            footerAdditionalField1Text.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
            footerAdditionalField1Text.setText(reportFooterConfiguration.getAdditionalField1().getValue());

            footerBand.addElement(footerAdditionalField1TitleText);
            footerBand.addElement(footerAdditionalField1Text);
        }

        if (nonNull(reportFooterConfiguration.getAdditionalField2())) {
            JRDesignStaticText footerAdditionalField2TitleText = new JRDesignStaticText();
            footerAdditionalField2TitleText.setWidth(FOOTER_ADDITIONAL_FIELD_2_WIDTH);
            footerAdditionalField2TitleText.setHeight(FOOTER_TEXT_FIELD_HEIGHT);
            footerAdditionalField2TitleText.setX(FOOTER_ADDITIONAL_FIELD_2_X);
            footerAdditionalField2TitleText.setY(FOOTER_TITLE_TEXT_FIELD_Y);
            footerAdditionalField2TitleText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
            footerAdditionalField2TitleText.setText(reportFooterConfiguration.getAdditionalField2().getName());

            JRDesignStaticText footerAdditionalField2Text = new JRDesignStaticText();
            footerAdditionalField2Text.setWidth(FOOTER_ADDITIONAL_FIELD_2_WIDTH);
            footerAdditionalField2Text.setHeight(FOOTER_TEXT_FIELD_HEIGHT);
            footerAdditionalField2Text.setX(FOOTER_ADDITIONAL_FIELD_2_X);
            footerAdditionalField2Text.setY(FOOTER_TEXT_FIELD_Y);
            footerAdditionalField2Text.setPdfFontName("Helvetica-Bold");
            footerAdditionalField2Text.setBold(Boolean.TRUE);
            footerAdditionalField2Text.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
            footerAdditionalField2Text.setText(reportFooterConfiguration.getAdditionalField2().getValue());

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

        JRDesignTextField pageNumberText = new JRDesignTextField();
        pageNumberText.setWidth(FOOTER_PAGE_NUMBER_TEXT_WIDTH);
        pageNumberText.setHeight(FOOTER_TEXT_FIELD_HEIGHT);
        pageNumberText.setX(FOOTER_PAGE_NUMBER_TEXT_X);
        pageNumberText.setY(FOOTER_PAGE_NUMBER_TEXT_Y);
        pageNumberText.setExpression(pageNumberExpression);
        pageNumberText.setEvaluationTime(EvaluationTimeEnum.MASTER);

        footerBand.addElement(pageNumberText);

        this.jasperDesign.setPageFooter(footerBand);
    }
}
