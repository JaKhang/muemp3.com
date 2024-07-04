package com.mue.repositories;

import com.mue.entities.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DistributorRepository extends JpaRepository<Distributor, UUID> {
    Page<Distributor> findAll(Specification<Distributor> specification, Pageable pageable);
}
