package com.werfen.report.service.pdf.util;

import net.sf.jasperreports.engine.design.JRDesignLine;

public class DesignLineBuilder {
    private int x;
    private int y;
    private int width;
    private int height;

    public DesignLineBuilder position(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }


    public JRDesignLine build() {
        JRDesignLine line = new JRDesignLine();

        line.setX(x);
        line.setY(y);
        line.setWidth(width);
        line.setHeight(height);
        return line;
    }
}