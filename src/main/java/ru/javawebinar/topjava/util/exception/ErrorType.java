package ru.javawebinar.topjava.util.exception;
import org.springframework.http.HttpStatus;
public enum ErrorType {
    APP_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"error.appError"),
    DATA_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY,"error.dataNotFound"),
    DATA_ERROR(HttpStatus.CONFLICT,"error.dataError"),
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY,"error.validationError");

    private final HttpStatus status;
    private final String titleError;
    ErrorType(HttpStatus httpStatus,String titleError) {
        this.status = httpStatus;
        this.titleError = titleError;
    }

    public HttpStatus getStatus(  ){
        return status;
    }

    public String getTitleError(  ){
        return titleError;
    }
}
