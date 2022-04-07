package com.search.api.service.local;

import com.search.api.model.dto.local.NaverLocalResponseDto;
import com.search.api.model.dto.local.NaverLocalDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public final class NaverLocalService extends LocalService {

    @Value("${naver.local.url}")
    private String naverApiUrl;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private final static String LOCAL_API_PATH = "/v1/search/local.json";

    @Override
    public List<NaverLocalDto> getLocalList(String q) {
        List<NaverLocalDto> localList = new ArrayList<>();

        String uri = naverApiUrl.concat(LOCAL_API_PATH);

        // parameter set
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.set("query", q);
        paramMap.set("display", REQUEST_SIZE);

        // header set
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-Naver-Client-Id", clientId);
        headerMap.put("X-Naver-Client-Secret", clientSecret);

        NaverLocalResponseDto responseDto = getLocalResponse(uri, paramMap, headerMap, NaverLocalResponseDto.class);
        if (responseDto != null && !CollectionUtils.isEmpty(responseDto.getItems())) {
            responseDto.getItems().forEach(dto -> {
                localList.add(NaverLocalDto.builder()
                        .placeName(dto.getTitle())
                        .roadAddressName(dto.getRoadAddress())
                        .build());
            });
        }

        return localList;
    }
}
