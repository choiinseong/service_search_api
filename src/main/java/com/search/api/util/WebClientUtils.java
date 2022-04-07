package com.search.api.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Map;

@Slf4j
@Component
public class WebClientUtils {

    private static final int HTTP_CONNECT_TIMEOUT = 10000;
    private static final int HTTP_READ_TIMEOUT = 10000;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        try {
            HttpComponentsClientHttpRequestFactory requestFactory = this.buildHttpComponentsClientHttpRequestFactory();
            restTemplate = new RestTemplate(requestFactory);
        } catch (Exception e) {
            log.error("WebClientUtils Init Error");
        }
    }

    private HttpComponentsClientHttpRequestFactory buildHttpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
        requestFactory.setReadTimeout(HTTP_READ_TIMEOUT);

        HttpClient httpClient = HttpClients.custom()
                .setMaxConnTotal(100)       // IP:PORT 관계없이 전체 커넥션 수
                .setMaxConnPerRoute(50)     // IP:PORT 마다 커넥션 수
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .disableCookieManagement()
                .build();

        requestFactory.setHttpClient(httpClient);

        return requestFactory;
    }

    public <T> ResponseEntity<T> getResponseEntity(String uri, Map<String, String> headerMap, Class<T> responseType)
            throws Exception {
        String unescapeUri = HtmlUtils.htmlUnescape(uri);
        URI buildUri = new URI(unescapeUri);

        if (!CollectionUtils.isEmpty(headerMap)) {
            // 헤더가 있는 경우
            HttpHeaders headers = new HttpHeaders();
            for (Map.Entry<String, String> headerMapEntry : headerMap.entrySet()) {
                headers.add(headerMapEntry.getKey(), headerMapEntry.getValue());
            }

            return restTemplate.exchange(buildUri, HttpMethod.GET, new HttpEntity(headers), responseType);
        } else {
            return restTemplate.getForEntity(buildUri, responseType);
        }
    }
}
