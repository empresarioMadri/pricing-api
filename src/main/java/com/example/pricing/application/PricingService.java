package com.example.pricing.application;

import com.example.pricing.infrastructure.persistence.PriceEntity;
import com.example.pricing.infrastructure.persistence.PriceJpaRepository;
import com.example.pricing.infrastructure.web.dto.PriceResponse;
import com.example.pricing.infrastructure.web.error.PriceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PricingService {
    private final PriceJpaRepository repo;

    public PricingService(PriceJpaRepository repo) {
        this.repo = repo;
    }

    public PriceResponse getApplicablePrice(LocalDateTime at, long productId, int brandId) {
        PriceEntity e = repo.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanOrderByPriorityDesc(
                productId, brandId, at, at
        );
        if (e == null) throw new PriceNotFoundException(productId, brandId, at);

        return new PriceResponse(
                e.getProductId(),
                e.getBrandId(),
                e.getPriceList(),
                e.getStartDate(),
                e.getEndDate(),
                e.getPrice(),
                e.getCurr()
        );
    }
}
