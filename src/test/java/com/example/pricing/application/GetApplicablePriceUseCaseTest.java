package com.example.pricing.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.pricing.application.exception.PriceNotFoundException;
import com.example.pricing.domain.model.Currency;
import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.ports.PriceRepositoryPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class GetApplicablePriceUseCaseTest {
  private final PriceRepositoryPort repo = mock(PriceRepositoryPort.class);
  private final GetApplicablePriceUseCase useCase = new GetApplicablePriceUseCase(repo);

  @Test
  void devuelve_precio_cuando_existe() {
    LocalDateTime at = LocalDateTime.parse("2020-06-14T10:00:00");
    Price expected =
        Price.of(
            1,
            LocalDateTime.parse("2020-06-14T00:00:00"),
            LocalDateTime.parse("2020-12-31T23:59:59"),
            1,
            35455L,
            0,
            new BigDecimal("35.50"),
            Currency.EUR);
    when(repo.findApplicable(at, 35455L, 1)).thenReturn(expected);

    Price p = useCase.execute(at, 35455L, 1);

    assertEquals(1, p.getBrandId());
    assertEquals(35455L, p.getProductId());
  }

  @Test
  void lanza_not_found_si_repo_no_encuentra() {
    LocalDateTime at = LocalDateTime.parse("2020-06-14T10:00:00");
    when(repo.findApplicable(at, 35455L, 1)).thenReturn(null);
    assertThrows(PriceNotFoundException.class, () -> useCase.execute(at, 35455L, 1));
  }
}
