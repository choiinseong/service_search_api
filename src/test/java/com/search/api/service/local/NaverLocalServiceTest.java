package com.search.api.service.local;

import com.search.api.model.dto.local.NaverLocalDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NaverLocalServiceTest {

    @Autowired
    private NaverLocalService naverLocalService;

    @Test
    @DisplayName("네이버 지역 조회 테스트")
    void getLocalListTest() {
        String q = "이디야 전북대점";
        List<NaverLocalDto> localList = naverLocalService.getLocalList(q);
        Assertions.assertNotNull(localList, "localList is null");

        // 이디야 전북대점은 1건만 존재
        Assertions.assertTrue(localList.size() == 1, "list size not equals");
    }
}
