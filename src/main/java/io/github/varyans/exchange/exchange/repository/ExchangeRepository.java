package io.github.varyans.exchange.exchange.repository;

import io.github.varyans.exchange.exchange.entity.ExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRepository extends JpaRepository<ExchangeEntity, Long> {


    List<ExchangeEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
