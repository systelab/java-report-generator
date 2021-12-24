package com.werfen.report.service.template;

import com.werfen.report.model.FormReportContent;
import com.werfen.report.model.FormReportField;
import com.werfen.report.model.FormReportSection;
import com.werfen.report.model.PageFormat;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

import java.awt.*;

public class FormReportTemplateBuilder extends BaseReportTemplateBuilder {

    public static final int SECTION_SEPARATOR_BEGIN = 5;
    public static final int SECTION_SEPARATOR_END = 10;
    private static final int BAND_WIDTH = PageFormat.A4.getWidth() - (20 * 2);
    private static final String TITLE_FONT_NAME = "Helvetica-Bold";
    private static final int TITLE_HEIGHT = 23;
    private static final int TITLE_LEFT_PADDING = 9;

    public static final int FIELD_HEIGHT = 14;
    public static final int FIELD_LABEL_X = 10;
    public static final int FIELD_VALUE_X = 134;

    public void addForm(FormReportContent formReportContent) {
        formReportContent.getSections().stream().forEach(section -> this.addSection(section, 0));
    }

    private void addSection(FormReportSection formReportSection, int sectionDepth) {
        this.addTitle(formReportSection.getTitle(), sectionDepth);

        this.addSeparator(formReportSection.getFields().isEmpty() ? 0 : SECTION_SEPARATOR_BEGIN);
        formReportSection.getFields().stream().forEach(field -> this.addField(field, 0));
        this.addSeparator(formReportSection.getFields().isEmpty() ? 0 : SECTION_SEPARATOR_END);

        formReportSection.getSubsections().stream().forEach(subsection -> this.addSection(subsection, sectionDepth + 1));
    }

    private void addTitle(String title, int sectionDepth) {
        JRDesignStaticText titleBox = new JRDesignStaticText();
        titleBox.setX(0);
        titleBox.setY(0);
        titleBox.setWidth(BAND_WIDTH);
        titleBox.setHeight(TITLE_HEIGHT);
        titleBox.setMode(ModeEnum.OPAQUE);
        titleBox.setBackcolor(this.getTitleColor(sectionDepth));

        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(TITLE_LEFT_PADDING);
        titleText.setY(0);
        titleText.setWidth(BAND_WIDTH - TITLE_LEFT_PADDING);
        titleText.setHeight(TITLE_HEIGHT);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        titleText.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        titleText.setText(title);
        titleText.setMode(ModeEnum.TRANSPARENT);
        titleText.setPdfFontName(TITLE_FONT_NAME);

        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(TITLE_HEIGHT);
        titleBand.setSplitType(SplitTypeEnum.STRETCH);
        titleBand.addElement(titleBox);
        titleBand.addElement(titleText);

        ((JRDesignSection) this.jasperDesign.getDetailSection()).addBand(titleBand);
    }

    private Color getTitleColor(int sectionDepth) {
        switch (sectionDepth) {
            case 0:
                return new Color(218, 217, 238);
            case 1:
                return new Color(243, 242, 249);
            case 2:
                return new Color(251, 251, 251);
            default:
                return new Color(255, 255, 255);
        }
    }

    private void addField(FormReportField formReportField, int fieldDepth) {
        JRDesignStaticText labelText = new JRDesignStaticText();
        labelText.setX(FIELD_LABEL_X * (fieldDepth + 1));
        labelText.setY(0);
        labelText.setHeight(FIELD_HEIGHT);
        labelText.setWidth(BAND_WIDTH / 4);
        labelText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        labelText.setText(formReportField.getLabel());

        JRDesignStaticText valueText = new JRDesignStaticText();
        valueText.setX(FIELD_VALUE_X);
        valueText.setY(0);
        valueText.setHeight(FIELD_HEIGHT);
        valueText.setWidth(BAND_WIDTH / 4);
        valueText.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        valueText.setText(formReportField.getValue());

        JRDesignBand fieldBand = new JRDesignBand();
        fieldBand.setHeight(FIELD_HEIGHT);
        fieldBand.setSplitType(SplitTypeEnum.STRETCH);
        fieldBand.addElement(labelText);
        fieldBand.addElement(valueText);

        ((JRDesignSection) this.jasperDesign.getDetailSection()).addBand(fieldBand);

        formReportField.getSubfields().stream().forEach(subfield -> this.addField(subfield, fieldDepth + 1));
    }

    private void addSeparator(int height) {
        if (height > 0) {
            JRDesignBand separatorBand = new JRDesignBand();
            separatorBand.setHeight(height);
            separatorBand.setSplitType(SplitTypeEnum.STRETCH);
            ((JRDesignSection) this.jasperDesign.getDetailSection()).addBand(separatorBand);
        }
    }
}
