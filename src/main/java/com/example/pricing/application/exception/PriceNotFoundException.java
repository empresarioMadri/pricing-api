package com.example.pricing.application.exception;

import java.time.LocalDateTime;
import lombok.Getter;

/** Excepción de aplicación: no conoce HTTP ni frameworks de presentación. */
@Getter
public class PriceNotFoundException extends RuntimeException {
  private final long productId;
  private final int brandId;
  private final LocalDateTime at;

  public PriceNotFoundException(long productId, int brandId, LocalDateTime at) {
    super("No applicable price for product=" + productId + " brand=" + brandId + " at=" + at);
    this.productId = productId;
    this.brandId = brandId;
    this.at = at;
  }
}
