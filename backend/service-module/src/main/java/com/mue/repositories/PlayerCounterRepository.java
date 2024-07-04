package com.mue.repositories;

import com.mue.entities.PlayerCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerCounterRepository extends JpaRepository<PlayerCounter, UUID> {
}
