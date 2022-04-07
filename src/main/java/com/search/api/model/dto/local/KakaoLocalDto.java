package com.search.api.model.dto.local;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class KakaoLocalDto extends LocalDto {
    public String[] getPlaceNameArr() {
        return getPlaceName().split(" ");
    }
}
