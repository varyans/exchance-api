package io.github.varyans.exchange.exchange.repository;

import io.github.varyans.exchange.exchange.entity.ExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExchangeRepository extends JpaRepository<ExchangeEntity, UUID> {
}
