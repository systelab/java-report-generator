package com.werfen.report.util;

import net.sf.jasperreports.engine.design.JRDesignRectangle;

public class DesignRectangleBuilder {
    private int x;
    private int y;
    private int width;
    private int height;

    public DesignRectangleBuilder position(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }


    public JRDesignRectangle build() {
        JRDesignRectangle rectangle = new JRDesignRectangle();

        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        return rectangle;
    }
}