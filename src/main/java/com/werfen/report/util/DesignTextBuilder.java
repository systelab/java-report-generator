package com.werfen.report.util;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

public class DesignTextBuilder {
    private String text;
    private JRExpression expression;

    private EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.NOW;

    private int x;
    private int y;
    private int width;
    private int height;
    private String font;
    private String encoding;
    private Float fontSize;
    private Boolean bold;
    private HorizontalTextAlignEnum horizontalTextAlign;

    public DesignTextBuilder text(String text) {
        this.text = text;
        return this;
    }

    public DesignTextBuilder expression(JRExpression expression) {
        this.expression = expression;
        return this;
    }

    public DesignTextBuilder evaluationTime(EvaluationTimeEnum evaluationTime) {
        this.evaluationTime = evaluationTime;
        return this;
    }

    public DesignTextBuilder position(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public DesignTextBuilder horizontalTextAlign(HorizontalTextAlignEnum horizontalTextAlign) {
        this.horizontalTextAlign = horizontalTextAlign;
        return this;
    }

    public DesignTextBuilder font(String font) {
        this.font = font;
        return this;
    }



    public DesignTextBuilder encoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public DesignTextBuilder fontSize(Float fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public DesignTextBuilder bold() {
        this.bold = Boolean.TRUE;
        return this;
    }

    public JRDesignStaticText buildStaticText() {
        JRDesignStaticText staticText = new JRDesignStaticText();
        if (text != null) {
            staticText.setText(text);
        }
        staticText.setX(x);
        staticText.setY(y);
        staticText.setWidth(width);
        staticText.setHeight(height);
        if (horizontalTextAlign != null)
            staticText.setHorizontalTextAlign(horizontalTextAlign);
        if (font != null)
            staticText.setPdfFontName(font);
        if (encoding != null)
            staticText.setPdfEncoding(encoding);
        if (fontSize != null)
            staticText.setFontSize(fontSize);
        if (bold != null)
            staticText.setBold(bold);

        return staticText;
    }

    public JRDesignTextField buildTextField() {
        JRDesignTextField textField = new JRDesignTextField();
        if (expression != null) {
            textField.setExpression(expression);
        }
        if (evaluationTime != null) {
            textField.setEvaluationTime(evaluationTime);
        }

        textField.setX(x);
        textField.setY(y);
        textField.setWidth(width);
        textField.setHeight(height);
        if (horizontalTextAlign != null)
            textField.setHorizontalTextAlign(horizontalTextAlign);
        if (font != null)
            textField.setPdfFontName(font);
        if (encoding != null)
            textField.setPdfEncoding(encoding);
        if (fontSize != null)
            textField.setFontSize(fontSize);
        if (bold != null)
            textField.setBold(bold);

        return textField;
    }
}