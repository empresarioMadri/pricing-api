package com.example.pricing.infrastructure.persistence;

import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.ports.PriceRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PriceJpaAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository jpa;

    public PriceJpaAdapter(PriceJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Price findApplicable(LocalDateTime at, long productId, int brandId) {
        PriceEntity e = jpa
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanOrderByPriorityDesc(
                        productId, brandId, at, at);
        return (e == null) ? null : e.toDomain();
    }

}
