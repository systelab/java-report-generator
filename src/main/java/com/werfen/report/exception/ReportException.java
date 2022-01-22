package com.werfen.report.exception;

public class ReportException extends Exception {

    public ReportException(Throwable cause) {
        super("Exception while generating a report.", cause);
    }

}
