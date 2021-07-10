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
import ua.com.foxminded.university.entities.dto.rest.GroupDtoRequest;

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
class GroupRestControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/api/groups";
    private static final String GET_ALL_INCORRECT_URL_TEMPLATE = "/api/groupssss";
    private static final String GET_BY_ID_URL_TEMPLATE = "/api/groups/1";
    private static final String GET_BY_ID_INCORRECT_URL_TEMPLATE = "/api/groupssss/5";

    private static final String POST_URL_TEMPLATE = "/api/groups";
    private static final String PATCH_URL_TEMPLATE = "/api/groups/1";
    private static final String DELETE_URL_TEMPLATE = "/api/groups/2";
    private static final String DELETE_INCORRECT_URL_TEMPLATE = "/api/groupsss/incorrect";


    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRetrieveAllGroupsFromDbWhensGetAllGroups() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", is(JSON_SIZE_VALUE_2)))
            .andExpect(jsonPath("$.[0].id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].name", is(GROUP_1_NAME_VALUE)))
            .andExpect(jsonPath("$.[0].cathedraId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].cathedraName", is(CATHEDRA_1_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].id", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].name", is(GROUP_2_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].cathedraId", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].cathedraName", is(CATHEDRA_2_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionOnBadRequestWhenGetAllGroups() throws Exception {
        mockMvc.perform(get(GET_ALL_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldRetrieveGroupFromDbWhenGetGroupByID() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.name", is(GROUP_1_NAME_VALUE)))
            .andExpect(jsonPath("$.cathedraId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.cathedraName", is(CATHEDRA_1_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenGetGroupById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldAddNewGroupToDb() throws Exception {
        GroupDtoRequest groupDtoRequest = new GroupDtoRequest();
        groupDtoRequest.setName(GROUP_3_NAME_VALUE);
        groupDtoRequest.setCathedraId(ID_1_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(groupDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(GROUP_3_NAME_VALUE)))
            .andExpect(jsonPath("$.cathedraId", is(ID_1_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenSaveGroupRequestedWithInvalidParameters() throws Exception {
        GroupDtoRequest groupDtoRequest = new GroupDtoRequest();
        groupDtoRequest.setName(GROUP_1_NAME_VALUE);
        groupDtoRequest.setCathedraId(ID_0_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(groupDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldUpdateGroupByIdInDb() throws Exception {
        GroupDtoRequest groupDtoRequest = new GroupDtoRequest();
        groupDtoRequest.setName(GROUP_3_NAME_VALUE);
        groupDtoRequest.setCathedraId(ID_1_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(groupDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(GROUP_3_NAME_VALUE)))
            .andExpect(jsonPath("$.cathedraId", is(ID_1_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenEditGroupRequestedWithInvalidParameters() throws Exception {
        GroupDtoRequest groupDtoRequest = new GroupDtoRequest();
        groupDtoRequest.setName(EMPTY_STRING);
        groupDtoRequest.setCathedraId(ID_1_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(groupDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDeleteGroupFromDb() throws Exception {
        mockMvc.perform(delete(DELETE_URL_TEMPLATE))
            .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenDeleteGroupOnBadRequest() throws Exception {
        mockMvc.perform(delete(DELETE_INCORRECT_URL_TEMPLATE))
            .andExpect(status().is4xxClientError());
    }
}