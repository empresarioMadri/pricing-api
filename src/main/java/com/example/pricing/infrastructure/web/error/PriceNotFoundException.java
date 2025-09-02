package com.example.pricing.infrastructure.web.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    public PriceNotFoundException(long productId, int brandId, LocalDateTime at) {
        super("No applicable price for product=" + productId + " brand=" + brandId + " at=" + at);
    }
    public HttpStatus getStatus() { return status; }
}
