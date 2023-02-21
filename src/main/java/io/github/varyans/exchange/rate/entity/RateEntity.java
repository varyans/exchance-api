package io.github.varyans.exchange.rate.entity;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "RateEntity", indexes = {
        @Index(name = "idx_rateentity_date", columnList = "date")
})
@Data
public class RateEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumCurrency base;
    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "rate_mapping",
            joinColumns = {@JoinColumn(name = "rate_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "currency")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "rate")
    private Map<EnumCurrency, Double> rates;
}
