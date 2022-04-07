package com.search.api.system.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

@Getter
@Setter
@NoArgsConstructor
public class SearchApiException extends RuntimeException {

    private HttpStatus statusCode;
    private String message;
    private SearchApiErrorCode errorCode;

    public SearchApiException(SearchApiErrorCode errorCode) {
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = errorCode.getMessage();
        this.errorCode = errorCode;
    }

    public SearchApiException(HttpStatus statusCode) {
        this.statusCode = statusCode;
        this.message = statusCode.getReasonPhrase();
        this.errorCode = SearchApiErrorCode.SRC0001;
    }

    public SearchApiException(Exception e) {
        if (e instanceof SearchApiException) {
            SearchApiException exception = (SearchApiException) e;
            this.statusCode = exception.statusCode;
            this.message = exception.getMessage();
            this.errorCode = exception.errorCode;
        } else {
            this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            this.errorCode = SearchApiErrorCode.SRC0001;
            this.message = getErrorMsg(e) ;
        }
    }

    private String getErrorMsg(Exception e) {
        if (e.getMessage() == null) {
            return e + "";
        } else {
            if (isIgnoreException(e)) {
                return "INTERNAL_SERVER_ERROR";
            } else {
                return e.getMessage();
            }
        }
    }

    private boolean isIgnoreException(Exception e) {
        if (e instanceof HttpMessageNotReadableException) {
            return true;
        }
        return false;
    }
}
