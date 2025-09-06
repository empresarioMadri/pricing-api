package com.example.pricing.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class PriceJpaRepositoryIT {

  @Autowired private PriceJpaRepository repo;

  @Test
  void recupera_top_priority_en_rango() {
    LocalDateTime at = LocalDateTime.parse("2020-06-14T16:00:00");

    PriceEntity e =
        repo
            .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanOrderByPriorityDesc(
                35455L, 1, at, at);

    assertThat(e).isNotNull();
    assertThat(e.getPriceList()).isEqualTo(2);
    assertThat(e.getPrice()).isNotNull();
  }
}
