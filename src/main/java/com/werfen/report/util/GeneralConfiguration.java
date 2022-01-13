package com.werfen.report.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class GeneralConfiguration {
    @Getter
    @Setter
    private static String defaultNullString = "-";


}
