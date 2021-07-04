package ua.com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.dto.CathedraDtoRequest;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.properties")
class CathedraRestControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/api/cathedras";
    private static final String GET_ALL_INCORRECT_URL_TEMPLATE = "/api/cathedrassss";
    private static final String GET_BY_ID_URL_TEMPLATE = "/api/cathedras/1";
    private static final String GET_BY_ID_INCORRECT_URL_TEMPLATE = "/api/cathedrassss/5";

    private static final String POST_URL_TEMPLATE = "/api/cathedras";
    private static final String PATCH_URL_TEMPLATE = "/api/cathedras/1";
    private static final String DELETE_URL_TEMPLATE = "/api/cathedras/2";
    private static final String DELETE_INCORRECT_URL_TEMPLATE = "/api/cathedrasss/incorrect";


    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRetrieveAllCathedrasFromDbWhensGetAllCathedras() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", is(JSON_SIZE_VALUE_2)))
            .andExpect(jsonPath("$.[0].id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].name", is(CATHEDRA_1_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].id", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].name", is(CATHEDRA_2_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionOnBadRequestWhenGetAllCathedras() throws Exception {
        mockMvc.perform(get(GET_ALL_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldRetrieveCathedraFromDbWhenGetCathedraByID() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.name", is(CATHEDRA_1_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenGetCathedraById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldAddNewCathedraToDb() throws Exception {
        CathedraDtoRequest cathedraDtoRequest = new CathedraDtoRequest();
        cathedraDtoRequest.setName(CATHEDRA_3_NAME_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(cathedraDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(CATHEDRA_3_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenSaveCathedraRequestedWithInvalidParameters() throws Exception {
        CathedraDtoRequest cathedraDtoRequest = new CathedraDtoRequest();
        cathedraDtoRequest.setName(EMPTY_STRING);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(cathedraDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldUpdateCathedraByIdInDb() throws Exception {
        CathedraDtoRequest cathedraDtoRequest = new CathedraDtoRequest();
        cathedraDtoRequest.setName(CATHEDRA_3_NAME_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(cathedraDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(CATHEDRA_3_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenEditCathedraRequestedWithInvalidParameters() throws Exception {
        CathedraDtoRequest cathedraDtoRequest = new CathedraDtoRequest();
        cathedraDtoRequest.setName(EMPTY_STRING);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(cathedraDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDeleteCathedraFromDb() throws Exception {
        mockMvc.perform(delete(DELETE_URL_TEMPLATE))
            .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenDeleteCathedraOnBadRequest() throws Exception {
        mockMvc.perform(delete(DELETE_INCORRECT_URL_TEMPLATE))
            .andExpect(status().is4xxClientError());
    }
}