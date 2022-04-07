package com.search.api.model.dto.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HttpResponse {
    private boolean success; // 성공여부
    private Object data; // 응답 데이터
    private int code; // 응답 코드
    private String errorCode; // 오류 코드
    private String errorMessage; // 실패시 에러 메시지
}
