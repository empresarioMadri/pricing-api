package com.example.pricing.infrastructure.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PriceControllerErrorsIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void bad_request_por_fecha_invalida() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .queryParam("applicationDate", "2020-06-14 10:00:00") // espacio -> formato inválido
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void not_found_cuando_no_hay_precio() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .queryParam("applicationDate", "2019-01-01T00:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(statements = {
            "INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) " +
                    "VALUES (1, '2020-06-20 09:00:00', '2020-06-20 11:00:00', 99, 35455, 2, -1.00, 'EUR')"
    })
    void unprocessable_entidad_invalida_en_bd() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .queryParam("applicationDate", "2020-06-20T09:30:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isUnprocessableEntity());
    }
}
