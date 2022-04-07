package com.search.api.service.place;

import com.search.api.model.dto.place.PlaceDto;

public interface PlaceService {
    PlaceDto getPlaceList(String q);
}
