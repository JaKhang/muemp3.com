package com.mue.services;

import com.mue.core.domain.ApiQuery;
import com.mue.payload.request.DistributorRequest;
import com.mue.payload.response.DistributorResponse;
import com.mue.payload.response.InfiniteListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DistributorService {
    InfiniteListResponse<DistributorResponse> findAll(Pageable pageable, List<ApiQuery> apiQueries);

    DistributorResponse findOne(UUID id);

    UUID create(DistributorRequest request);

    void update(UUID id, DistributorRequest request);

    void softDelete(UUID id);
}
