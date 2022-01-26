package com.werfen.report.exception;

public class ReportEmptyException extends RuntimeException {

    public ReportEmptyException() {
        super("report-empty");
    }

    @Override
    public String getLocalizedMessage() {
        return "Report is empty.";
    }
}

