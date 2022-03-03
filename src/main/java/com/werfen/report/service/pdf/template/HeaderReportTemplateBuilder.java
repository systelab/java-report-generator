package com.werfen.report.service.pdf.template;

import com.werfen.report.model.GridReportField;
import com.werfen.report.model.ReportHeaderConfiguration;
import com.werfen.report.service.pdf.util.DesignImageBuilder;
import com.werfen.report.service.pdf.util.DesignLineBuilder;
import com.werfen.report.service.pdf.util.DesignRectangleBuilder;
import com.werfen.report.service.pdf.util.DesignTextBuilder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;

import static java.lang.Math.round;

public class HeaderReportTemplateBuilder {

    public static final String BOLD_FONT = "Helvetica-Bold";
    public static final String TITLE_LOGO_PARAMETER = "TITLE_LOGO_PATH";
    protected static final int PAGE_MARGIN = 20;
    private static final int TITLE_MARGIN_X = 10;
    private static final int TITLE_HEIGHT = 42;
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
    private static final int TITLE_X = TITLE_MARGIN_X;

    private static final int HEADER_MARGIN_X = 10;
    private static final int HEADER_HEIGHT = 48;
    private static final int HEADER_FIELDS_HEIGHT = 11;
    private static final int HEADER_FIELDS_TITLE_Y = 7;
    private static final int HEADER_FIELDS_Y = 21;
    private static final int HEADER_FRAME_HEIGHT = 38;
    private static final int HEADER_FRAME_X = 0;
    private static final int HEADER_FRAME_Y = 0;
    private static final int HEADER_FIELDS_INITIAL_X = HEADER_MARGIN_X;

    public void addHeader(JasperDesign jasperDesign, ReportHeaderConfiguration reportHeaderConfiguration) throws JRException {
        final int FIELD_COUNT = reportHeaderConfiguration.getFields().size();
        final int FIELD_MARGIN_COUNT = FIELD_COUNT + 1;
        final int BAND_WIDTH = jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);
        final int TITLE_LINE_SEPARATOR_X = BAND_WIDTH - TITLE_LOGO_WIDTH - (TITLE_MARGIN_X * 2);
        final int TITLE_LOGO_X = TITLE_LINE_SEPARATOR_X + TITLE_MARGIN_X;
        final int TITLE_TEXT_WIDTH = TITLE_LINE_SEPARATOR_X - (TITLE_MARGIN_X * 2);
        final int HEADER_FIELDS_WIDTH = round((float) (BAND_WIDTH - (HEADER_MARGIN_X * FIELD_MARGIN_COUNT)) / FIELD_COUNT);


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

        JRDesignImage titleLogoImage = new DesignImageBuilder(jasperDesign)
                .expression(titleLogoExpression)
                .position(TITLE_LOGO_X, TITLE_LOGO_Y, TITLE_LOGO_WIDTH, TITLE_LOGO_HEIGHT).build();

        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(TITLE_HEIGHT);
        titleBand.setSplitType(SplitTypeEnum.STRETCH);
        titleBand.addElement(titleFrame);
        titleBand.addElement(titleFrameSeparator);
        titleBand.addElement(titleText);
        titleBand.addElement(titleLogoImage);

        jasperDesign.addParameter(titleLogoParameter);
        jasperDesign.setTitle(titleBand);

        JRDesignBand headerBand = new JRDesignBand();
        headerBand.setHeight(HEADER_HEIGHT);
        headerBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignRectangle headerFrame = new DesignRectangleBuilder()
                .position(HEADER_FRAME_X, HEADER_FRAME_Y, BAND_WIDTH, HEADER_FRAME_HEIGHT).build();

        headerBand.addElement(headerFrame);

        int xPosition = HEADER_FIELDS_INITIAL_X;
        for (GridReportField field : reportHeaderConfiguration.getFields()) {
            addField(headerBand, field, xPosition, HEADER_FIELDS_TITLE_Y, HEADER_FIELDS_Y, HEADER_FIELDS_WIDTH, HEADER_FIELDS_HEIGHT);
            xPosition = xPosition + HEADER_FIELDS_WIDTH + HEADER_MARGIN_X;
        }
        jasperDesign.setPageHeader(headerBand);
    }

    private void addField(JRDesignBand band, GridReportField field, int xPos, int yTilePos, int yFieldPos, int fieldWidth, int fieldHeight) {
        JRDesignStaticText headerField1TitleText = new DesignTextBuilder()
                .text(field.getName())
                .position(xPos, yTilePos, fieldWidth, fieldHeight)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        JRDesignStaticText headerField1Text = new DesignTextBuilder()
                .text(field.getValue())
                .position(xPos, yFieldPos, fieldWidth, fieldHeight)
                .font(BOLD_FONT)
                .bold()
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        band.addElement(headerField1TitleText);
        band.addElement(headerField1Text);
    }
}
