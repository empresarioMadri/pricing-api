package com.example.pricing.infrastructure.web.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PriceResponse(
        long productId,
        int brandId,
        int priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal finalPrice,
        String currency) {
}
