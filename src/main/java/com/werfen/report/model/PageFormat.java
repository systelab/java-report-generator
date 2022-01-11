package com.werfen.report.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PageFormat {

    A4(595, 842),
    LETTER(612, 792),
    FULL_SCREEN_1080P(1920, 1080),
    FULL_SCREEN_720P(1080, 720);

    private final int width;
    private final int height;
}
