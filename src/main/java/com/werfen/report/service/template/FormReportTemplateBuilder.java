package com.werfen.report.service.template;

import com.werfen.report.model.FormReportData;
import com.werfen.report.model.FormReportField;
import com.werfen.report.model.FormReportSection;
import com.werfen.report.util.DesignTextBuilder;
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
    private static final String TITLE_FONT_NAME = "Helvetica-Bold";
    private static final int TITLE_HEIGHT = 23;
    private static final int TITLE_LEFT_PADDING = 9;

    public static final int FIELD_HEIGHT = 14;
    public static final int FIELD_LABEL_X = 10;
    public static final int FIELD_VALUE_X = 134;

    public void addForm(FormReportData formReportData) {
        formReportData.getSections().forEach(section -> this.addSection(section, 0));
    }

    private void addSection(FormReportSection formReportSection, int sectionDepth) {
        this.addTitle(formReportSection.getTitle(), sectionDepth);

        this.addSeparator(formReportSection.getFields().isEmpty() ? 0 : SECTION_SEPARATOR_BEGIN);
        formReportSection.getFields().forEach(field -> this.addField(field, 0));
        this.addSeparator(formReportSection.getFields().isEmpty() ? 0 : SECTION_SEPARATOR_END);

        formReportSection.getSubsections().forEach(subsection -> this.addSection(subsection, sectionDepth + 1));
    }

    private void addTitle(String title, int sectionDepth) {
        final int BAND_WIDTH = this.jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);

        JRDesignStaticText titleBox = new DesignTextBuilder()
                .position(0,0, BAND_WIDTH, TITLE_HEIGHT)
                .mode(ModeEnum.OPAQUE)
                .backgroundColor(this.getTitleColor(sectionDepth))
                .buildStaticText();

        JRDesignStaticText titleText = new DesignTextBuilder()
                .position(TITLE_LEFT_PADDING, 0, BAND_WIDTH - TITLE_LEFT_PADDING, TITLE_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .verticalTextAlign(VerticalTextAlignEnum.MIDDLE)
                .mode(ModeEnum.TRANSPARENT)
                .font(TITLE_FONT_NAME)
                .text(title)
                .buildStaticText();

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
        final int BAND_WIDTH = this.jasperDesign.getPageWidth() - (PAGE_MARGIN * 2);
        JRDesignStaticText labelText = new DesignTextBuilder()
                .position(FIELD_LABEL_X * (fieldDepth + 1), 0, BAND_WIDTH / 4, FIELD_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .text(formReportField.getLabel())
                .buildStaticText();

        JRDesignStaticText valueText = new DesignTextBuilder()
                .position(FIELD_VALUE_X, 0, BAND_WIDTH / 4, FIELD_HEIGHT)
                .horizontalTextAlign(HorizontalTextAlignEnum.LEFT)
                .text(formReportField.getValue())
                .buildStaticText();

        JRDesignBand fieldBand = new JRDesignBand();
        fieldBand.setHeight(FIELD_HEIGHT);
        fieldBand.setSplitType(SplitTypeEnum.STRETCH);
        fieldBand.addElement(labelText);
        fieldBand.addElement(valueText);

        ((JRDesignSection) this.jasperDesign.getDetailSection()).addBand(fieldBand);

        formReportField.getSubfields().forEach(subfield -> this.addField(subfield, fieldDepth + 1));
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
