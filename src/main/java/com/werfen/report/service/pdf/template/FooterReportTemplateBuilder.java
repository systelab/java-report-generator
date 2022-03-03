package com.werfen.report.service.pdf.template;

import com.werfen.report.model.GridReportField;
import com.werfen.report.model.ReportFooterConfiguration;
import com.werfen.report.service.pdf.util.DesignRectangleBuilder;
import com.werfen.report.service.pdf.util.DesignTextBuilder;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.ExpressionTypeEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;

import static java.lang.Math.round;

public class FooterReportTemplateBuilder {

    public static final String BOLD_FONT = "Helvetica-Bold";
    protected static final int PAGE_MARGIN = 20;
    private static final int PAGE_NUMBER_MARGIN_X = 14;

    private static final int PAGE_NUMBER_WIDTH = 81;

    private static final int FOOTER_MARGIN_X = 10;
    private static final int FOOTER_TEXT_FIELD_HEIGHT = 9;
    private static final int FOOTER_FIELDS_TITLE_Y = 8;
    private static final int FOOTER_FIELDS_Y = 17;
    private static final int FOOTER_PAGE_NUMBER_TEXT_WIDTH = 32;
    private static final int FOOTER_PAGE_NUMBER_TEXT_Y = 13;
    private static final int FOOTER_FRAME_HEIGHT = 34;
    private static final int FOOTER_FRAME_X = 0;
    private static final int FOOTER_FRAME_Y = 0;
    private static final int FOOTER_HEIGHT = 34;
    private static final int FOOTER_FIELDS_INITIAL_X = FOOTER_MARGIN_X;


    public void addFooter(JasperDesign jasperDesign, ReportFooterConfiguration configuration) {
        final int effectiveWidth = getPageEffectiveWidth(jasperDesign);
        final int footerFieldWidth = getFieldWidth(jasperDesign, configuration);
        final int footerPageNumberTextX = effectiveWidth - PAGE_NUMBER_MARGIN_X - FOOTER_PAGE_NUMBER_TEXT_WIDTH;

        final JRDesignBand footerBand = getFooterBand(effectiveWidth);

        int xPosition = FOOTER_FIELDS_INITIAL_X;
        for (GridReportField field : configuration.getFields()) {
            addField(footerBand, field, xPosition, FOOTER_FIELDS_TITLE_Y, FOOTER_FIELDS_Y, footerFieldWidth, FOOTER_TEXT_FIELD_HEIGHT);
            xPosition = xPosition + footerFieldWidth + FOOTER_MARGIN_X;
        }

        if (configuration.isShowPageNumbers()) {
            addPageNumber(footerBand, footerPageNumberTextX);
        }

        jasperDesign.setPageFooter(footerBand);
    }

    private JRDesignBand getFooterBand(int width) {
        JRDesignBand footerBand = new JRDesignBand();
        footerBand.setHeight(FOOTER_HEIGHT);
        footerBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignRectangle footerFrame = new DesignRectangleBuilder()
                .position(FOOTER_FRAME_X, FOOTER_FRAME_Y, width, FOOTER_FRAME_HEIGHT).build();

        footerBand.addElement(footerFrame);
        return footerBand;
    }

    private void addPageNumber(JRDesignBand band, int xPosition) {
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
                .position(xPosition, FOOTER_PAGE_NUMBER_TEXT_Y, FOOTER_PAGE_NUMBER_TEXT_WIDTH, FOOTER_TEXT_FIELD_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildTextField();

        band.addElement(pageNumberText);
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

    private int getFieldWidth(JasperDesign jasperDesign, ReportFooterConfiguration configuration){
        final int fieldCount = configuration.getFields().size();
        final int fieldMarginCountX = fieldCount + 1;
        final int footerFieldsWidth =  getPageEffectiveWidth(jasperDesign) - PAGE_NUMBER_WIDTH - (FOOTER_MARGIN_X * 2);
        return round((float) (footerFieldsWidth - (FOOTER_MARGIN_X * fieldMarginCountX)) / fieldCount);
    }

    private int getPageEffectiveWidth(JasperDesign jasperDesign){
        return jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);
    }
}
