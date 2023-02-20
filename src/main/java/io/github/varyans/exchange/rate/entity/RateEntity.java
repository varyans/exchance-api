package io.github.varyans.exchange.rate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RateEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String base;
    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "rate_mapping",
            joinColumns = {@JoinColumn(name = "rate_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "currency")
    @Column(name = "rate")
    private Map<String, Double> rates;
}
