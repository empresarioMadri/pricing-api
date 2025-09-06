package com.example.pricing.infrastructure.web;

import com.example.pricing.application.GetApplicablePriceUseCase;
import com.example.pricing.domain.model.Price;
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

    private final GetApplicablePriceUseCase useCase;

    public PriceController(GetApplicablePriceUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public PriceResponse getPrice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull LocalDateTime applicationDate,
            @RequestParam @Min(1) long productId,
            @RequestParam @Min(1) int brandId
    ) {
        Price p = useCase.execute(applicationDate, productId, brandId);
        return new PriceResponse(
                p.getProductId(),
                p.getBrandId(),
                p.getPriceList(),
                p.getStartDate(),
                p.getEndDate(),
                p.getAmount(),
                p.getCurrency().name()
        );
    }
}