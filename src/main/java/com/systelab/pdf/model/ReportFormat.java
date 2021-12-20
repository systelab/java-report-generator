package com.systelab.pdf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReportFormat {
    @JsonProperty("PDF") PDF,
    @JsonProperty("EXCEL") EXCEL,
}
