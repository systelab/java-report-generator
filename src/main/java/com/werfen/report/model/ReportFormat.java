package com.werfen.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReportFormat {
    @JsonProperty("PDF") PDF(".pdf"),
    @JsonProperty("EXCEL") EXCEL(".xlsx");

    private final String fileExtension;
}
