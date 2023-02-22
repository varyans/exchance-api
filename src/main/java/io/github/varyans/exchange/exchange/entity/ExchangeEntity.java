package io.github.varyans.exchange.exchange.entity;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "ExchangeEntity", indexes = {
        @Index(name = "idx_exchange_date", columnList = "date")
})
@Data
public class ExchangeEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumCurrency base;

    private Double amount;
    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "exchange_mapping",
            joinColumns = {@JoinColumn(name = "exchange_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "currency")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "rate")
    private Map<EnumCurrency, Double> rates;
}
