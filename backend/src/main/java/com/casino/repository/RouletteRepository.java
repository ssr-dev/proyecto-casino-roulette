package com.casino.repository;

import com.casino.model.Roulette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RouletteRepository extends JpaRepository<Roulette, Long> {
    Optional<Roulette> findByActive(boolean active);
}