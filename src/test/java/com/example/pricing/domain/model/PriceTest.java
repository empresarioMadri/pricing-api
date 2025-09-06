package com.example.pricing.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void of_crea_aggregate_valido() {
        Price p = Price.of(1,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                1, 35455L, 0, new BigDecimal("35.50"), Currency.EUR);
        assertEquals(35455L, p.getProductId());
        assertTrue(p.appliesAt(LocalDateTime.parse("2020-06-14T10:00:00")));
    }

    @Test
    void of_lanza_si_fechas_invalidas() {
        assertThrows(IllegalArgumentException.class, () ->
                Price.of(1,
                        LocalDateTime.parse("2020-06-14T10:00:00"),
                        LocalDateTime.parse("2020-06-14T09:59:59"),
                        1, 35455L, 0, new BigDecimal("35.50"), Currency.EUR));
    }

    @Test
    void of_lanza_si_importe_negativo() {
        assertThrows(IllegalArgumentException.class, () ->
                Price.of(1,
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"),
                        1, 35455L, 0, new BigDecimal("-1"), Currency.EUR));
    }
}
