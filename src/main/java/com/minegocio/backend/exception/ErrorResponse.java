package com.minegocio.backend.exception;

import java.time.LocalDate;

public class ErrorResponse {
    private LocalDate localDate;
    private int status;
    private String error;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDate localDate, int status, String error, String message) {
        this.localDate = localDate;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
