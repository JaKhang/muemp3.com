package com.mue.services.impl;

import com.mue.converters.ObjectConverter;
import com.mue.payload.response.ObjectResponse;
import com.mue.repositories.CountryRepository;
import com.mue.services.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {


    private final CountryRepository countryRepository;
    private final ObjectConverter converter;
    @Override
    public List<ObjectResponse> findAll() {
        return countryRepository.findAll().stream().map(converter::convert).toList();
    }
}
