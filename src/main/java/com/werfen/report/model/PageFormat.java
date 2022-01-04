package com.werfen.report.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PageFormat {

    A4(595, 842),
    LETTER(612, 792),
    DATA_DEFINED(-1, -1);

    private final int width;
    private final int height;

    public boolean isDataDefined() {
        return width < 0 && height < 0;
    }
}
