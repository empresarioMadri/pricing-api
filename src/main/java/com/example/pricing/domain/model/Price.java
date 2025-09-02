package com.example.pricing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Price {
    private final int brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int priceList;
    private final long productId;
    private final int priority;
    private final BigDecimal amount;
    private final Currency currency;


    public boolean appliesAt(LocalDateTime at) {
        return (at.isEqual(startDate) || at.isAfter(startDate)) && at.isBefore(endDate);
    }
}
