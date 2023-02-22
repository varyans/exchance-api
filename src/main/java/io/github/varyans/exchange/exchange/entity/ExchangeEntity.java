package io.github.varyans.exchange.exchange.entity;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "ExchangeEntity", indexes = {
        @Index(name = "idx_exchange_date", columnList = "date")
})
public class ExchangeEntity {
    @Id
    @GeneratedValue
    private UUID id;

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
