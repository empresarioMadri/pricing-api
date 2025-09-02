package com.example.pricing.infrastructure.web;

import com.example.pricing.application.PricingService;
import com.example.pricing.infrastructure.web.dto.PriceResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@Validated
public class PriceController {

    private final PricingService service;

    public PriceController(PricingService service) {
        this.service = service;
    }

    @GetMapping
    public PriceResponse getPrice(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @NotNull LocalDateTime applicationDate,
            @RequestParam @Min(1) long productId,
            @RequestParam @Min(1) int brandId
    ) {
        return service.getApplicablePrice(applicationDate, productId, brandId);
    }
}
