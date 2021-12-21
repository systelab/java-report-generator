package com.werfen.report.service;

import com.werfen.report.model.GridColumnConfiguration;
import com.werfen.report.model.GridReportColumnWidth;
import com.werfen.report.model.PageFormat;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import static java.lang.Math.round;

public class GridReportTemplateBuilder extends BaseReportTemplateBuilder {

    private static final int BAND_WIDTH = PageFormat.A4.getWidth() - (20 * 2);
    private static final int ROW_SEPARATOR_LINE_THICKNESS = 1;
    private static final int CELL_UNIT_WIDTH = round((float) BAND_WIDTH / 12);
    private static final int CELL_UNIT_HEIGHT = 32;
    private static final int CELL_ITEM_HEIGHT = 9;
    private static final int CELL_TITLE_OFFSET_Y = 8;
    private static final int CELL_VALUE_OFFSET_Y = 17;
    private static final String FIELD_TEXT_PDF_FONT = "Helvetica-Bold";
    private static final String FIELD_TEXT_PDF_ENCODING = "UTF-8";
    private final MessageSource messageSource;
    private Locale locale;

    public GridReportTemplateBuilder(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void addGrid(List<GridColumnConfiguration> gridColumnConfigurations) throws JRException {
        JRDesignBand rowBand = new JRDesignBand();
        rowBand.setHeight(CELL_UNIT_HEIGHT);
        rowBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignLine rowSeparator = new JRDesignLine();
        rowSeparator.setX(0);
        rowSeparator.setY(0);
        rowSeparator.setWidth(BAND_WIDTH);
        rowSeparator.setHeight(ROW_SEPARATOR_LINE_THICKNESS);
        rowBand.addElement(rowSeparator);

        int currentColumn = 0;
        int currentRow = 0;
        for (int i = 0; i < gridColumnConfigurations.size(); i++) {

            GridColumnConfiguration gridColumnConfiguration = gridColumnConfigurations.get(i);
            this.addCell(rowBand, gridColumnConfiguration, currentColumn, currentRow);

            currentColumn += gridColumnConfiguration.getWidth().getValue();

            boolean isLastColumn = (i == gridColumnConfigurations.size() - 1);
            if (!isLastColumn) {
                int nextColumnWidth = gridColumnConfigurations.get(i + 1).getWidth().getValue();
                if (currentColumn + nextColumnWidth > GridReportColumnWidth.COLUMN_WIDTH_12.getValue()) {
                    currentColumn = 0;
                    currentRow++;
                }
            }
        }

        rowBand.setHeight((currentRow + 1) * CELL_UNIT_HEIGHT);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(rowBand);
    }

    private void addCell(JRDesignBand rowBand, GridColumnConfiguration gridColumnConfiguration, int currentColumn, int currentRow) throws JRException {
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(currentColumn * CELL_UNIT_WIDTH);
        titleText.setY((currentRow * CELL_UNIT_HEIGHT) + CELL_TITLE_OFFSET_Y);
        titleText.setWidth(gridColumnConfiguration.getWidth().getValue() * CELL_UNIT_WIDTH);
        titleText.setHeight(CELL_ITEM_HEIGHT);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        titleText.setText(messageSource.getMessage(gridColumnConfiguration.getTranslationKey(), new Object[0], gridColumnConfiguration.getName(), locale));
        rowBand.addElement(titleText);

        JRDesignField fieldValue = new JRDesignField();
        fieldValue.setName(gridColumnConfiguration.getName());
        fieldValue.setValueClass(String.class);
        super.jasperDesign.addField(fieldValue);

        JRDesignExpression fieldValueExpression = new JRDesignExpression();
        fieldValueExpression.setText("$F{" + gridColumnConfiguration.getName() + "}");

        JRDesignTextField valueText = new JRDesignTextField();
        valueText.setX(currentColumn * CELL_UNIT_WIDTH);
        valueText.setY((currentRow * CELL_UNIT_HEIGHT) + CELL_VALUE_OFFSET_Y);
        valueText.setWidth(gridColumnConfiguration.getWidth().getValue() * CELL_UNIT_WIDTH);
        valueText.setHeight(CELL_ITEM_HEIGHT);
        valueText.setBold(Boolean.TRUE);
        valueText.setPdfFontName(FIELD_TEXT_PDF_FONT);
        valueText.setPdfEncoding(FIELD_TEXT_PDF_ENCODING);
        valueText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        valueText.setExpression(fieldValueExpression);
        rowBand.addElement(valueText);
    }


}
