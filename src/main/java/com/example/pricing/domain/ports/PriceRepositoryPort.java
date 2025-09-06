package com.example.pricing.domain.ports;

import com.example.pricing.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceRepositoryPort {
    Price findApplicable(LocalDateTime at, long productId, int brandId);
}
