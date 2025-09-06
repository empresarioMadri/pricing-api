package com.example.pricing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Price {
  private final int brandId;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final int priceList;
  private final long productId;
  private final int priority;
  private final BigDecimal amount;
  private final Currency currency;

  private Price(
      int brandId,
      LocalDateTime startDate,
      LocalDateTime endDate,
      int priceList,
      long productId,
      int priority,
      BigDecimal amount,
      Currency currency) {
    this.brandId = brandId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.priceList = priceList;
    this.productId = productId;
    this.priority = priority;
    this.amount = amount;
    this.currency = currency;
  }

  /** Fábrica con invariantes del dominio. */
  public static Price of(
      int brandId,
      LocalDateTime startDate,
      LocalDateTime endDate,
      int priceList,
      long productId,
      int priority,
      BigDecimal amount,
      Currency currency) {
    Objects.requireNonNull(startDate, "startDate");
    Objects.requireNonNull(endDate, "endDate");
    Objects.requireNonNull(amount, "amount");
    Objects.requireNonNull(currency, "currency");
    if (!startDate.isBefore(endDate)) {
      throw new IllegalArgumentException("startDate must be before endDate");
    }
    if (brandId < 1 || productId < 1) {
      throw new IllegalArgumentException("brandId/productId must be >= 1");
    }
    if (priority < 0 || priceList < 0) {
      throw new IllegalArgumentException("priority/priceList must be >= 0");
    }
    if (amount.signum() < 0) {
      throw new IllegalArgumentException("amount must be >= 0");
    }
    return new Price(brandId, startDate, endDate, priceList, productId, priority, amount, currency);
  }

  public boolean appliesAt(LocalDateTime at) {
    return (at.isEqual(startDate) || at.isAfter(startDate)) && at.isBefore(endDate);
  }
}
