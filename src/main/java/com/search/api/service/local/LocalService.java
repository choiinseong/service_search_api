package com.search.api.service.local;

import com.search.api.system.exception.SearchApiErrorCode;
import com.search.api.system.exception.SearchApiException;
import com.search.api.util.WebClientUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.Map;

@Slf4j
public abstract class LocalService {
    @Autowired
    protected WebClientUtils webClientUtils;
    protected final static String REQUEST_SIZE = "5";

    public abstract <T> T getLocalList(String q);

    protected <T> T getLocalResponse(String uri, MultiValueMap<String, String> paramMap
            , Map<String, String> headerMap, Class<T> responseType) {

        try {
            // 검색어(q) encoding 처리
            if (paramMap.get("query") != null) {
                paramMap.set("query", URLEncoder.encode(paramMap.get("query").toString(), "UTF-8"));
            }

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                    .queryParams(paramMap);

            ResponseEntity<String> response =
                    webClientUtils.getResponseEntity(builder.build().toUriString(), headerMap, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                JavaType type = objectMapper.getTypeFactory().constructType(responseType);

                return objectMapper.readValue(response.getBody(), type);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SearchApiException(SearchApiErrorCode.SRC0001);
        }

        return null;
    }
}
