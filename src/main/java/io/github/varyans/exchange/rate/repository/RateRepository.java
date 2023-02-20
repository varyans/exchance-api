package io.github.varyans.exchange.rate.repository;

import io.github.varyans.exchange.rate.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<RateEntity, Long> {
}
