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

import static java.lang.Math.round;
import static java.util.Objects.nonNull;

public class BaseReportTemplateBuilder {

    public static final String BOLD_FONT = "Helvetica-Bold";
    protected static final int PAGE_MARGIN = 20;
    private static final int TITLE_MARGIN_X = 10;
    private static final int HEADER_MARGIN_X = 10;
    private static final int FOOTER_MARGIN_X = 10;

    public static final String TITLE_LOGO_PARAMETER = "TITLE_LOGO_PATH";
    private static final int TITLE_HEIGHT = 42;
    private static final int PAGE_NUMBER_MARGIN_X = 14;
    private static final int TITLE_Y = 12;
    private static final int TITLE_LOGO_WIDTH = 81;
    private static final int TITLE_LOGO_HEIGHT = 26;
    private static final int TITLE_LOGO_Y = 9;
    private static final int TITLE_TEXT_HEIGHT = 18;
    private static final int TITLE_FRAME_HEIGHT = 42;
    private static final int TITLE_FRAME_X = 0;
    private static final int TITLE_FRAME_Y = 0;
    private static final int TITLE_LINE_SEPARATOR_WIDTH = 1;
    private static final int TITLE_LINE_SEPARATOR_HEIGHT = 42;
    private static final int TITLE_LINE_SEPARATOR_Y = 0;
    private static final Float TITLE_TEXT_FONT_SIZE = 14f;

    private static final int HEADER_HEIGHT = 48;
    private static final int HEADER_FIELDS_HEIGHT = 11;
    private static final int HEADER_FIELDS_TITLE_Y = 7;
    private static final int HEADER_FIELDS_Y = 21;
    private static final int TITLE_X = TITLE_MARGIN_X;
    private static final int HEADER_FRAME_HEIGHT = 38;
    private static final int HEADER_FRAME_X = 0;
    private static final int HEADER_FRAME_Y = 0;

    private static final int FOOTER_TEXT_FIELD_HEIGHT = 9;
    private static final int HEADER_FIELD_1_X = HEADER_MARGIN_X;
    private static final int FOOTER_FIELDS_TITLE_Y = 8;
    private static final int FOOTER_FIELDS_Y = 17;
    private static final int FOOTER_PAGE_NUMBER_TEXT_WIDTH = 32;
    private static final int FOOTER_PAGE_NUMBER_TEXT_Y = 13;
    private static final int FOOTER_FRAME_HEIGHT = 34;
    private static final int FOOTER_FRAME_X = 0;
    private static final int FOOTER_FRAME_Y = 0;
    private static final int FOOTER_HEIGHT = 34;

    private static final String STYLE_NAME = "default";
    private static final String STYLE_ENCODING = "UTF-8";
    private static final Float STYLE_FONT_SIZE = 7f;
    private static final int FOOTER_FIELD_1_X = FOOTER_MARGIN_X;

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
        final int BAND_WIDTH = this.jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);
        final int TITLE_LINE_SEPARATOR_X = BAND_WIDTH - TITLE_LOGO_WIDTH - (TITLE_MARGIN_X * 2);
        final int TITLE_LOGO_X = TITLE_LINE_SEPARATOR_X + TITLE_MARGIN_X;
        final int TITLE_TEXT_WIDTH = TITLE_LINE_SEPARATOR_X - (TITLE_MARGIN_X * 2);
        final int HEADER_FIELDS_WIDTH = round((float) (BAND_WIDTH - (HEADER_MARGIN_X * 5)) / 4);
        final int HEADER_FIELD_2_X = HEADER_FIELD_1_X + HEADER_FIELDS_WIDTH + HEADER_MARGIN_X;
        final int HEADER_FIELD_3_X = HEADER_FIELD_2_X + HEADER_FIELDS_WIDTH + HEADER_MARGIN_X;
        final int HEADER_FIELD_4_X = HEADER_FIELD_3_X + HEADER_FIELDS_WIDTH + HEADER_MARGIN_X;


        JRDesignRectangle titleFrame = new DesignRectangleBuilder()
                .position(TITLE_FRAME_X, TITLE_FRAME_Y, BAND_WIDTH, TITLE_FRAME_HEIGHT).build();

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
                .position(HEADER_FRAME_X, HEADER_FRAME_Y, BAND_WIDTH, HEADER_FRAME_HEIGHT).build();

        headerBand.addElement(headerFrame);

        if (nonNull(reportHeaderConfiguration.getField1())) {
            addField(headerBand, reportHeaderConfiguration.getField1(), HEADER_FIELD_1_X, HEADER_FIELDS_TITLE_Y, HEADER_FIELDS_Y, HEADER_FIELDS_WIDTH);
        }

        if (nonNull(reportHeaderConfiguration.getField2())) {
            addField(headerBand, reportHeaderConfiguration.getField2(), HEADER_FIELD_2_X, HEADER_FIELDS_TITLE_Y, HEADER_FIELDS_Y, HEADER_FIELDS_WIDTH);
        }

        if (nonNull(reportHeaderConfiguration.getField3())) {
            addField(headerBand, reportHeaderConfiguration.getField3(), HEADER_FIELD_3_X, HEADER_FIELDS_TITLE_Y, HEADER_FIELDS_Y, HEADER_FIELDS_WIDTH);
        }

        if (nonNull(reportHeaderConfiguration.getField2())) {
            addField(headerBand, reportHeaderConfiguration.getField4(), HEADER_FIELD_4_X, HEADER_FIELDS_TITLE_Y, HEADER_FIELDS_Y, HEADER_FIELDS_WIDTH);
        }
        this.jasperDesign.setPageHeader(headerBand);
    }

    public void addFooter(ReportFooterConfiguration reportFooterConfiguration) {
        final int BAND_WIDTH = this.jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);
        final int FOOTER_EFFECTIVE_WIDTH = BAND_WIDTH - TITLE_LOGO_WIDTH - (TITLE_MARGIN_X * 2);
        final int FOOTER_FIELDS_WIDTH = round((float) (FOOTER_EFFECTIVE_WIDTH - (FOOTER_MARGIN_X * 4)) / 3);
        final int FOOTER_FIELD_2_X = FOOTER_FIELD_1_X + FOOTER_FIELDS_WIDTH + FOOTER_MARGIN_X;
        final int FOOTER_FIELD_3_X = FOOTER_FIELD_2_X + FOOTER_FIELDS_WIDTH + FOOTER_MARGIN_X;
        final int FOOTER_PAGE_NUMBER_TEXT_X = BAND_WIDTH - PAGE_NUMBER_MARGIN_X - FOOTER_PAGE_NUMBER_TEXT_WIDTH;

        JRDesignBand footerBand = new JRDesignBand();
        footerBand.setHeight(FOOTER_HEIGHT);
        footerBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignRectangle footerFrame = new DesignRectangleBuilder()
                .position(FOOTER_FRAME_X, FOOTER_FRAME_Y, BAND_WIDTH, FOOTER_FRAME_HEIGHT).build();

        footerBand.addElement(footerFrame);

        if (nonNull(reportFooterConfiguration.getField1())) {
            addField(footerBand, reportFooterConfiguration.getField1(), FOOTER_FIELD_1_X, FOOTER_FIELDS_TITLE_Y, FOOTER_FIELDS_Y, FOOTER_FIELDS_WIDTH);
        }

        if (nonNull(reportFooterConfiguration.getField2())) {
            addField(footerBand, reportFooterConfiguration.getField2(), FOOTER_FIELD_2_X, FOOTER_FIELDS_TITLE_Y, FOOTER_FIELDS_Y, FOOTER_FIELDS_WIDTH);
        }

        if (nonNull(reportFooterConfiguration.getField3())) {
            addField(footerBand, reportFooterConfiguration.getField3(), FOOTER_FIELD_3_X, FOOTER_FIELDS_TITLE_Y, FOOTER_FIELDS_Y, FOOTER_FIELDS_WIDTH);
        }

        addPageNumber(FOOTER_PAGE_NUMBER_TEXT_X, footerBand);

        this.jasperDesign.setPageFooter(footerBand);
    }

    private void addPageNumber(int FOOTER_PAGE_NUMBER_TEXT_X, JRDesignBand footerBand) {
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
    }

    private void addField(JRDesignBand band, ReportField field, int xPos, int yTilePos, int yFieldPos, int fieldWidth) {
        JRDesignStaticText headerField1TitleText = new DesignTextBuilder()
                .text(field.getName())
                .position(xPos, yTilePos, fieldWidth, HEADER_FIELDS_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        JRDesignStaticText headerField1Text = new DesignTextBuilder()
                .text(field.getValue())
                .position(xPos, yFieldPos, fieldWidth, HEADER_FIELDS_HEIGHT)
                .font(BOLD_FONT)
                .bold()
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        band.addElement(headerField1TitleText);
        band.addElement(headerField1Text);
    }
}
