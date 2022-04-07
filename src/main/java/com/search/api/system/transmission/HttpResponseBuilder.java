package com.search.api.system.transmission;

import com.search.api.model.dto.common.HttpResponse;
import com.search.api.system.exception.SearchApiException;
import org.springframework.http.HttpStatus;

public class HttpResponseBuilder {
    public static HttpResponse buildSuccessResponse(Object data) {
        HttpResponse response = new HttpResponse();
        response.setSuccess(true);
        response.setData(data);
        response.setCode(HttpStatus.OK.value());
        return response;
    }

    public static HttpResponse buildFailResponse(SearchApiException exception) {
        HttpResponse response = new HttpResponse();
        response.setSuccess(false);
        response.setErrorMessage(exception.getMessage());
        response.setCode(exception.getStatusCode().value());
        response.setErrorCode(exception.getErrorCode().getCode());
        return response;
    }
}
