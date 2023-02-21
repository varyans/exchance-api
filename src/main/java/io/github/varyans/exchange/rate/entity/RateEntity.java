package io.github.varyans.exchange.rate.entity;

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
    private String base;
    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "rate_mapping",
            joinColumns = {@JoinColumn(name = "rate_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "currency")
    @Column(name = "rate")
    private Map<String, Double> rates;
}
