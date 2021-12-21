package com.werfen.report.model;

public enum PageFormat {

    A4(595, 842),
    LETTER(612, 792);

    private final int width;
    private final int height;

    PageFormat(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
