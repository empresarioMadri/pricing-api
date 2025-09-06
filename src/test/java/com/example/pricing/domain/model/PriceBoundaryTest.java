package com.example.pricing.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PriceBoundaryTest {

  private Price base() {
    return Price.of(
        1,
        LocalDateTime.parse("2020-06-14T10:00:00"),
        LocalDateTime.parse("2020-06-14T12:00:00"),
        1,
        35455L,
        0,
        new BigDecimal("10.00"),
        Currency.EUR);
  }

  @Test
  void appliesAt_true_cuando_igual_a_start() {
    var p = base();
    assertTrue(p.appliesAt(LocalDateTime.parse("2020-06-14T10:00:00")));
  }

  @Test
  void appliesAt_false_cuando_igual_a_end() {
    var p = base();
    assertFalse(p.appliesAt(LocalDateTime.parse("2020-06-14T12:00:00")));
  }

  @Test
  void invariantes_ids_y_prioridad() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Price.of(
                0,
                LocalDateTime.parse("2020-06-14T10:00:00"),
                LocalDateTime.parse("2020-06-14T12:00:00"),
                1,
                35455L,
                0,
                new BigDecimal("10.00"),
                Currency.EUR));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Price.of(
                1,
                LocalDateTime.parse("2020-06-14T10:00:00"),
                LocalDateTime.parse("2020-06-14T12:00:00"),
                -1,
                35455L,
                -1,
                new BigDecimal("10.00"),
                Currency.EUR));
  }
}
