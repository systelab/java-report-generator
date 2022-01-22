package com.werfen.report.service.pdf.template;

import com.werfen.report.model.GridColumnConfiguration;
import com.werfen.report.model.GridReportColumnWidth;
import com.werfen.report.service.pdf.util.DesignLineBuilder;
import com.werfen.report.service.pdf.util.DesignTextBuilder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;

import java.util.List;

import static java.lang.Math.round;

public class GridReportTemplateBuilder extends BaseReportTemplateBuilder {

    private static final int ROW_SEPARATOR_LINE_THICKNESS = 1;
    private static final int CELL_UNIT_HEIGHT = 32;
    private static final int CELL_ITEM_HEIGHT = 9;
    private static final int CELL_TITLE_OFFSET_Y = 8;
    private static final int CELL_VALUE_OFFSET_Y = 17;
    private static final String FIELD_TEXT_PDF_FONT = "Helvetica-Bold";
    private static final String FIELD_TEXT_PDF_ENCODING = "UTF-8";

    public void addGrid(List<GridColumnConfiguration> gridColumnConfigurations) throws JRException {
        final int BAND_WIDTH = this.jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);

        JRDesignBand rowBand = new JRDesignBand();
        rowBand.setHeight(CELL_UNIT_HEIGHT);
        rowBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignLine rowSeparator = new DesignLineBuilder().position(0, 0, BAND_WIDTH, ROW_SEPARATOR_LINE_THICKNESS).build();
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
        final int BAND_WIDTH = this.jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);
        final int CELL_UNIT_WIDTH = round((float) BAND_WIDTH / 12);

        JRDesignStaticText titleText = new DesignTextBuilder()
                .text(gridColumnConfiguration.getTranslation())
                .position(currentColumn * CELL_UNIT_WIDTH, (currentRow * CELL_UNIT_HEIGHT) + CELL_TITLE_OFFSET_Y, gridColumnConfiguration.getWidth().getValue() * CELL_UNIT_WIDTH, CELL_ITEM_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildStaticText();

        rowBand.addElement(titleText);

        JRDesignField fieldValue = new JRDesignField();
        fieldValue.setName(gridColumnConfiguration.getName());
        fieldValue.setValueClass(String.class);
        super.jasperDesign.addField(fieldValue);

        JRDesignExpression fieldValueExpression = new JRDesignExpression();
        fieldValueExpression.setText("$F{" + gridColumnConfiguration.getName() + "}");

        JRDesignTextField valueText = new DesignTextBuilder()
                .expression(fieldValueExpression)
                .position(currentColumn * CELL_UNIT_WIDTH, (currentRow * CELL_UNIT_HEIGHT) + CELL_VALUE_OFFSET_Y, gridColumnConfiguration.getWidth().getValue() * CELL_UNIT_WIDTH, CELL_ITEM_HEIGHT)
                .font(FIELD_TEXT_PDF_FONT).bold()
                .encoding(FIELD_TEXT_PDF_ENCODING)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .buildTextField();

        rowBand.addElement(valueText);
    }


}
