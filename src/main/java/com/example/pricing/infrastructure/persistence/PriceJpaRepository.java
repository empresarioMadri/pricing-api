package com.example.pricing.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
    PriceEntity findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanOrderByPriorityDesc(
            long productId,
            int brandId,
            LocalDateTime applicationDateForStart,
            LocalDateTime applicationDateForEnd
    );
}
