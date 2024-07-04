package com.mue.api;

import com.mue.core.domain.RestBody;
import com.mue.payload.response.ObjectResponse;
import com.mue.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryApi {
    private final CountryService countryService;

    @GetMapping

    public ResponseEntity<RestBody<?>> findAll(){

        List<ObjectResponse> countries = countryService.findAll();

        return ResponseEntity.ok(new RestBody<>(countries));

    }
}
