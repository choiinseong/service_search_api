package com.search.api.model.dto.local;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class LocalDto {
    protected String placeName; // 업체명
    protected String roadAddressName; // 도로명 주소
}
