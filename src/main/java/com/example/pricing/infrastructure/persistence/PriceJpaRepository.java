package com.example.pricing.infrastructure.persistence;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
  PriceEntity
      findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanOrderByPriorityDesc(
          long productId,
          int brandId,
          LocalDateTime applicationDateForStart,
          LocalDateTime applicationDateForEnd);
}
