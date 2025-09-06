package com.example.pricing.application;

import static org.junit.jupiter.api.Assertions.*;

import com.example.pricing.application.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PriceNotFoundExceptionTest {
  @Test
  void getters_y_mensaje() {
    var at = LocalDateTime.parse("2020-06-14T10:00:00");
    var ex = new PriceNotFoundException(35455L, 1, at);
    assertTrue(ex.getMessage().contains("35455"));
    assertEquals(35455L, ex.getProductId());
    assertEquals(1, ex.getBrandId());
    assertEquals(at, ex.getAt());
  }
}
