package com.asejnr.order_service.web.controllers;

import com.asejnr.order_service.AbstractIntegrationTest;
import com.asejnr.order_service.WithMockOAuth2User;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GetOrdersTests extends AbstractIntegrationTest {
    @Test
    @WithMockOAuth2User(username = "user")
    void shouldGetOrdersSuccessfully() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }
}
