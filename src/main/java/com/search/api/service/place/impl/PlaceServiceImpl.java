package com.search.api.service.place.impl;

import com.search.api.model.dto.place.PlaceDto;
import com.search.api.system.exception.SearchApiErrorCode;
import com.search.api.model.dto.local.KakaoLocalDto;
import com.search.api.model.dto.local.NaverLocalDto;
import com.search.api.service.local.KakaoLocalService;
import com.search.api.service.local.NaverLocalService;
import com.search.api.service.place.PlaceService;
import com.search.api.system.exception.SearchApiException;
import com.search.api.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private KakaoLocalService kakaoLocalService;

    @Autowired
    private NaverLocalService naverLocalService;

    @Override
    public PlaceDto getPlaceList(String q) {
        List<String> placeList = new ArrayList<>();

        try {
            List<KakaoLocalDto> kakaoLocalList = kakaoLocalService.getLocalList(q);
            List<NaverLocalDto> naverLocalList = naverLocalService.getLocalList(q);

            kakaoLocalList.forEach(kakaoLocal -> {
                String[] kakaoPlaceNameArr = kakaoLocal.getPlaceNameArr();

                for (String kakaoPlaceName : kakaoPlaceNameArr) {
                    for (NaverLocalDto naverLocalDto : naverLocalList) {
                        if (naverLocalDto.getPlaceName() != "") {
                            String naverPlaceName = StringUtils.removeTag(naverLocalDto.getPlaceName()).trim();

                            if (naverPlaceName.contains(kakaoPlaceName) || kakaoPlaceName.contains(naverPlaceName)) {
                                String kakaoAddressName = StringUtils.replaceAddressName(kakaoLocal.getRoadAddressName());
                                String naverAddressName = StringUtils.replaceAddressName(naverLocalDto.getRoadAddressName());
                                if (kakaoAddressName.equals(naverAddressName)) {
                                    naverLocalDto.setPlaceName("");
                                }

                            }
                        }
                    }
                }

                placeList.add(kakaoPlaceNameArr[0]);
            });

            // 중복 제거된 네이버 결과 add all
            if (!CollectionUtils.isEmpty(naverLocalList)) {
                placeList.addAll(naverLocalList.stream()
                        .filter(dto -> !dto.getPlaceName().equals(""))
                        .map(dto -> StringUtils.removeTag(dto.getPlaceName()))
                        .collect(Collectors.toList()));
            }
        } catch (Exception e) {
            throw new SearchApiException(SearchApiErrorCode.SRC0001);
        }


        return PlaceDto.builder().places(placeList).build();
    }
}
