package ru.letmerent.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.letmerent.core.dto.AuthRequest;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.yml")
@SpringBootTest
public class AuthControllerTest {
    private static final String USERNAME = "test";
    private static final String PASS = "1111";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void successAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(mapper.valueToTree(AuthRequest.builder()
                        .username(USERNAME)
                        .password(PASS)
                        .build()))))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION));
    }

    @Test
    public void badValidation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(mapper.valueToTree(AuthRequest.builder()
                        .username(USERNAME)
                        .build()))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void badCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(mapper.valueToTree(AuthRequest.builder()
                        .username(USERNAME)
                        .password("111111")
                        .build()))))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }
}