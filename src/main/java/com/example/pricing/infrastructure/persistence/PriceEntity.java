package com.example.pricing.infrastructure.persistence;

import com.example.pricing.domain.model.Currency;
import com.example.pricing.domain.model.Price;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
@Getter
@Setter
@NoArgsConstructor
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BRAND_ID", nullable = false)
    private int brandId;
    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;
    @Column(name = "PRICE_LIST", nullable = false)
    private int priceList;
    @Column(name = "PRODUCT_ID", nullable = false)
    private long productId;
    @Column(name = "PRIORITY", nullable = false)
    private int priority;
    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "CURR", nullable = false, length = 3)
    private String curr;

    public Price toDomain() {
        return new Price(brandId, startDate, endDate, priceList, productId, priority, price, Currency.valueOf(curr));
    }
}
