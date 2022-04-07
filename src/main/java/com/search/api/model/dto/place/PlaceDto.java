package com.search.api.model.dto.place;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PlaceDto {
    private List<String> places;
}
