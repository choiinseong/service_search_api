package com.search.api.system.exception;

import lombok.Getter;

@Getter
public enum SearchApiErrorCode {

    SRC0001("SRC0001", "시스템 에러가 발생했습니다."),
    SRC0002("SRC0002", "검색어를 입력해주세요.");

    private String code;
    private String message;

    SearchApiErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
