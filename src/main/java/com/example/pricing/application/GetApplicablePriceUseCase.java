package com.example.pricing.application;

import com.example.pricing.application.exception.PriceNotFoundException;
import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.ports.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GetApplicablePriceUseCase {

    private final PriceRepositoryPort repository;

    public GetApplicablePriceUseCase(PriceRepositoryPort repository) {
        this.repository = repository;
    }

    public Price execute(LocalDateTime at, long productId, int brandId) {
        Price price = repository.findApplicable(at, productId, brandId);
        if (price == null) {
            throw new PriceNotFoundException(productId, brandId, at);
        }
        return price;
    }
}
