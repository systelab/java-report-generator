package com.werfen.report.service.exception;

public class ReportEmptyException extends RuntimeException {

    public ReportEmptyException() {
        super("report-empty");
    }

    @Override
    public String getLocalizedMessage() {
        return String.format("Report is empty.");
    }
}
