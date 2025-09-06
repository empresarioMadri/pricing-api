package com.example.pricing.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PriceResponse(
    long productId,
    int brandId,
    int priceList,
    LocalDateTime startDate,
    LocalDateTime endDate,
    BigDecimal finalPrice,
    String currency) {}
