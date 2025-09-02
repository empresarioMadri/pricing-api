package com.example.pricing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PriceControllerIT {
    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> exampleRequests() {
        return Stream.of(
                // fecha,    priceList esperado, precio esperado
                Arguments.of("2020-06-14T10:00:00", 1, 35.50),
                Arguments.of("2020-06-14T16:00:00", 2, 25.45),
                Arguments.of("2020-06-14T21:00:00", 1, 35.50),
                Arguments.of("2020-06-15T10:00:00", 3, 30.50),
                Arguments.of("2020-06-16T21:00:00", 4, 38.95)
        );
    }

    @ParameterizedTest(name = "Req {index}: {0} -> priceList {1}, price {2}")
    @MethodSource("exampleRequests")
    void should_return_expected_price_for_examples(String applicationDate, int expectedPriceList, double expectedPrice)
            throws Exception {

        mockMvc.perform(
                        get("/api/v1/prices")
                                .queryParam("applicationDate", applicationDate)
                                .queryParam("productId", "35455")
                                .queryParam("brandId", "1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.finalPrice").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }
}
