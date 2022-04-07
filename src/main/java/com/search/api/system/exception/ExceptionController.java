package com.search.api.system.exception;

import com.search.api.model.dto.common.HttpResponse;
import com.search.api.system.transmission.HttpResponseBuilder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class ExceptionController {

    @RequestMapping(value = "/handling", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    HttpResponse resourceHandling(HttpServletRequest request) throws Exception {
        Exception e = (Exception) request.getAttribute("exception");
        if (e == null) {
            e = new SearchApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        SearchApiException exception = new SearchApiException(e);

        return HttpResponseBuilder.buildFailResponse(exception);
    }
}
