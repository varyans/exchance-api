package io.github.varyans.exchange.rate.repository;

import io.github.varyans.exchange.rate.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Long> {
    Optional<RateEntity> findOneByDate(LocalDate date);


}
