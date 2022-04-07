package com.search.api.model.dto.local;

import lombok.Data;

import java.util.List;

@Data
public class NaverLocalResponseDto {
    private String lastBuildDate; // 검색 결과 생성 시간
    private int total; // 문서 총 개수
    private int start; // 문서 시작
    private int display; // 검색 결과 개수
    List<Item> items; // 개별 검색 결과

    @Data
    public static class Item {
        private String title; // 업체, 기관명
        private String link; // 하이퍼텍스트 link
        private String category; // 업체, 기관의 분류 정보
        private String description; // 업체, 기관명에 대한 설명
        private String telephone; // 빈 문자열 반환. 과거에 제공되던 항목
        private String address; // 업체, 기관명의 주소
        private String roadAddress; // 업체, 기관명의 도로명 주소
        private String mapx; // 업체, 기관명 위치 정보의 x좌표를 제공
        private String mapy; // 업체, 기관명 위치 정보의 y좌표를 제공
    }
}
