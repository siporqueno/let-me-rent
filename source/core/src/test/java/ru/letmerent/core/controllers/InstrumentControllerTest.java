package ru.letmerent.core.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAllInstrumentsPaged() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/instruments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.instruments", hasSize(3)))
                .andExpect(jsonPath("$.instruments[0].id", is(1)))
                .andExpect(jsonPath("$.instruments[0].title", is("Шуруповерт")))
                .andExpect(jsonPath("$.instruments[0].brandName", is("BOSH")))
                .andExpect(jsonPath("$.instruments[0].price", is(1234.56)))
                .andExpect(jsonPath("$.instruments[0].fee", is(12.23)))
                .andExpect(jsonPath("$.instruments[0].ownerUsername", is("test")))
                .andExpect(jsonPath("$.instruments[0].categoryName", is("Ручной")));
    }
}
