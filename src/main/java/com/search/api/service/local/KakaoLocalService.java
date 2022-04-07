package com.search.api.service.local;

import com.search.api.model.dto.local.KakaoLocalResponseDto;
import com.search.api.model.dto.local.KakaoLocalDto;
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
public final class KakaoLocalService extends LocalService {

    @Value("${kakao.local.url}")
    private String kakaoApiUrl;

    @Value("${kakao.app.key}")
    private String appKey;

    private final static String LOCAL_API_PATH = "/v2/local/search/keyword.json";

    @Override
    public List<KakaoLocalDto> getLocalList(String q) {
        List<KakaoLocalDto> localList = new ArrayList<>();

        String uri = kakaoApiUrl.concat(LOCAL_API_PATH);

        // parameter set
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.set("query", q);
        paramMap.set("size", REQUEST_SIZE);

        // header set
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "KakaoAK ".concat(appKey));

        KakaoLocalResponseDto responseDto = getLocalResponse(uri, paramMap, headerMap, KakaoLocalResponseDto.class);
        if (responseDto != null && !CollectionUtils.isEmpty(responseDto.getDocuments())) {
            responseDto.getDocuments().forEach(dto -> {
                localList.add(KakaoLocalDto.builder()
                        .placeName(dto.getPlace_name())
                        .roadAddressName(dto.getRoad_address_name())
                        .build());
            });
        }

        return localList;
    }
}
