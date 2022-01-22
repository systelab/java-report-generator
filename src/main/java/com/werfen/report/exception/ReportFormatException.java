package com.werfen.report.exception;

public class ReportFormatException extends RuntimeException {

    private final String reportFormat;

    public ReportFormatException(String reportFormat) {
        super("invalid-report-format");
        this.reportFormat=reportFormat;
    }

    @Override
    public String getLocalizedMessage() {
        return "Report Format " + reportFormat + " is not currently supported";
    }
}

