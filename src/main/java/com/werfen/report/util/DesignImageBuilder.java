package com.werfen.report.util;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;

public class DesignImageBuilder {

    private final JasperDesign jasperDesign;
    private JRExpression expression;
    private int x;
    private int y;
    private int width;
    private int height;

    public DesignImageBuilder(JasperDesign jasperDesign) {
        this.jasperDesign = jasperDesign;
    }

    public DesignImageBuilder expression(JRExpression expression) {
        this.expression = expression;
        return this;
    }

    public DesignImageBuilder position(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }


    public JRDesignImage build() {
        JRDesignImage image = new JRDesignImage(jasperDesign);
        if (expression!=null)
            image.setExpression(expression);
        image.setX(x);
        image.setY(y);
        image.setWidth(width);
        image.setHeight(height);
        return image;
    }
}