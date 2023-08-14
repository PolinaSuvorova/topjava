package ru.javawebinar.topjava.util.exception;
public enum ErrorType {
    APP_ERROR("error.appError"),
    DATA_NOT_FOUND("error.dataNotFound"),
    DATA_ERROR("error.dataError"),
    VALIDATION_ERROR("error.validationError");

    private final String titleError;
    ErrorType(String titleError) {
        this.titleError = titleError;
    }

    @Override
    public String toString() {
        String titleError1 = titleError;
        return titleError1;
    }
}
