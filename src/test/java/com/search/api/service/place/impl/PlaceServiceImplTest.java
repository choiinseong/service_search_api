package com.search.api.service.place.impl;

import com.search.api.model.dto.place.PlaceDto;
import com.search.api.service.place.PlaceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlaceServiceImplTest {

    @Autowired
    private PlaceService placeService;

    @Test
    @DisplayName("장소 조회 테스트")
    void getPlaceList() {
        String q = "이디야 전북대점";
        PlaceDto placeDto = placeService.getPlaceList(q);
        Assertions.assertNotNull(placeDto, "placeDto is null");

        // 이디야 전북대점은 1건만 존재
        Assertions.assertTrue(placeDto.getPlaces().size() == 1, "list size not equals");
    }
}
