package com.search.api.controller.place;

import com.search.api.model.dto.common.HttpResponse;
import com.search.api.model.dto.place.PlaceDto;
import com.search.api.system.exception.SearchApiErrorCode;
import com.search.api.system.exception.SearchApiException;
import com.search.api.system.transmission.HttpResponseBuilder;
import com.search.api.service.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    /**
     * 장소 검색
     * @param q : 검색 질의어
     * @return
     */
    @GetMapping("")
    public HttpResponse find(@RequestParam String q) {



        if (q == null || q.trim().equals("")) {
            throw new SearchApiException(SearchApiErrorCode.SRC0002);
        }

        PlaceDto place = placeService.getPlaceList(q);
        return HttpResponseBuilder.buildSuccessResponse(place);
    }
}
