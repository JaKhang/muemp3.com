package com.mue.services.impl;

import com.mue.core.domain.ApiQuery;
import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.Distributor;
import com.mue.converters.Converter;
import com.mue.payload.request.DistributorRequest;
import com.mue.payload.response.DistributorResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.repositories.DistributorRepository;
import com.mue.services.DistributorService;
import com.mue.specifications.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DistributorServiceImpl implements DistributorService {

    private final Converter<DistributorResponse, DistributorResponse, Distributor, DistributorRequest> mapper;
    private final DistributorRepository distributorRepository;
    @Override
    public InfiniteListResponse<DistributorResponse> findAll(Pageable pageable, List<ApiQuery> apiQueries) {
        Specification<Distributor> specification = SpecificationBuilder.build(apiQueries);
        Page<Distributor> distributors = distributorRepository.findAll(specification, pageable);
        return new InfiniteListResponse<>(
                distributors.getNumberOfElements(),
                distributors.map(mapper::convertToDetails).getContent(),
                distributors.hasNext()
        );
    }

    @Override
    public DistributorResponse findOne(UUID id) {
        Distributor distributor = findByIdElseThrow(id);
        return mapper.convertToDetails(distributor);
    }

    @Override
    public UUID create(DistributorRequest request) {
        Distributor distributor = mapper.convertToEntity(request);
        return distributorRepository.save(distributor).getId();
    }

    @Override
    public void update(UUID id, DistributorRequest request) {
        Distributor distributor = findByIdElseThrow(id);
        Distributor newDis = mapper.convertToEntity(request);
        distributor.setName(newDis.getName());
        distributor.setAlias(newDis.getAlias());
        distributor.setCoverImage(newDis.getCoverImage());
        distributorRepository.save(distributor);
    }

    @Override
    public void softDelete(UUID id) {
        Distributor distributor = findByIdElseThrow(id);
        distributor.setDeleted(true);
        distributorRepository.save(distributor);
    }

    private Distributor findByIdElseThrow(UUID id){
        return distributorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Distributor", "ID", id));
    }
}
