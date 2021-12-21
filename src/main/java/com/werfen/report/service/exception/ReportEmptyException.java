package com.werfen.report.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportEmptyException extends RuntimeException {

    public ReportEmptyException() {
        super("report-empty");
    }

    @Override
    public String getLocalizedMessage() {
        return String.format("Report is empty.");
    }
}

