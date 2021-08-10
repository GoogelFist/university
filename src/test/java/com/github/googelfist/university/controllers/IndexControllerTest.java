package com.github.googelfist.university.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.properties")
class IndexControllerTest {
    private static final String INDEX_URL_TEMPLATE = "/";
    private static final String INDEX_VIEW_NAME = "/index";


    @Autowired
    public MockMvc mockMvc;

    @Test
    void shouldReturnCorrectedIndexPageWhenGetIndexPage() throws Exception {
        mockMvc.perform(get(INDEX_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(INDEX_VIEW_NAME));
    }
}