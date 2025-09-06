package com.example.pricing.infrastructure.web;

import static org.hamcrest.Matchers.closeTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PriceControllerIT {

  @Autowired private MockMvc mockMvc;

  static Stream<Arguments> casos() {
    return Stream.of(
        Arguments.of("2020-06-14T10:00:00", 1, 35.50),
        Arguments.of("2020-06-14T16:00:00", 2, 25.45),
        Arguments.of("2020-06-14T21:00:00", 1, 35.50),
        Arguments.of("2020-06-15T10:00:00", 3, 30.50),
        Arguments.of("2020-06-16T21:00:00", 4, 38.95));
  }

  @ParameterizedTest
  @MethodSource("casos")
  void contrato_ok(String applicationDate, int priceList, double price) throws Exception {
    mockMvc
        .perform(
            get("/api/v1/prices")
                .queryParam("applicationDate", applicationDate)
                .queryParam("productId", "35455")
                .queryParam("brandId", "1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(priceList))
        // JSON puede serializar 35.50 como 35.5 -> usamos tolerancia
        .andExpect(jsonPath("$.finalPrice").value(closeTo(price, 0.001)))
        .andExpect(jsonPath("$.currency").value("EUR"));
  }
}
