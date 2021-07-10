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
import ua.com.foxminded.university.entities.dto.rest.TeacherDtoRequest;

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
class TeacherRestControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/api/teachers";
    private static final String GET_ALL_INCORRECT_URL_TEMPLATE = "/api/teacherssss";
    private static final String GET_BY_ID_URL_TEMPLATE = "/api/teachers/1";
    private static final String GET_BY_ID_INCORRECT_URL_TEMPLATE = "/api/teacherssss/5";

    private static final String POST_URL_TEMPLATE = "/api/teachers";
    private static final String PATCH_URL_TEMPLATE = "/api/teachers/1";
    private static final String DELETE_URL_TEMPLATE = "/api/teachers/2";
    private static final String DELETE_INCORRECT_URL_TEMPLATE = "/api/teachersss/incorrect";


    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRetrieveAllTeachersFromDbWhensGetAllTeachers() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", is(JSON_SIZE_VALUE_2)))
            .andExpect(jsonPath("$.[0].id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].firstName", is(TEACHER_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.[0].lastName", is(TEACHER_1_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.[0].phone", is(TEACHER_1_PHONE_VALUE)))
            .andExpect(jsonPath("$.[0].qualification", is(QUALIFICATION_1_VALUE)))
            .andExpect(jsonPath("$.[0].cathedraId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].cathedraName", is(CATHEDRA_1_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].id", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].firstName", is(TEACHER_2_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].lastName", is(TEACHER_2_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].phone", is(TEACHER_2_PHONE_VALUE)))
            .andExpect(jsonPath("$.[1].qualification", is(QUALIFICATION_2_VALUE)))
            .andExpect(jsonPath("$.[1].cathedraId", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].cathedraName", is(CATHEDRA_2_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionOnBadRequestWhenGetAllTeachers() throws Exception {
        mockMvc.perform(get(GET_ALL_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldRetrieveTeacherFromDbWhenGetTeacherByID() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.firstName", is(TEACHER_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.lastName", is(TEACHER_1_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.phone", is(TEACHER_1_PHONE_VALUE)))
            .andExpect(jsonPath("$.qualification", is(QUALIFICATION_1_VALUE)))
            .andExpect(jsonPath("$.cathedraId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.cathedraName", is(CATHEDRA_1_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenGetTeacherById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldAddNewTeacherToDb() throws Exception {
        TeacherDtoRequest teacherDTORequest = new TeacherDtoRequest();
        teacherDTORequest.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDTORequest.setLastName(TEACHER_2_LAST_NAME_VALUE);
        teacherDTORequest.setPhone(TEACHER_1_PHONE_VALUE);
        teacherDTORequest.setQualification(QUALIFICATION_1_VALUE);
        teacherDTORequest.setCathedraId(ID_1_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(teacherDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is(TEACHER_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.lastName", is(TEACHER_2_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.phone", is(TEACHER_1_PHONE_VALUE)))
            .andExpect(jsonPath("$.qualification", is(QUALIFICATION_1_VALUE)))
            .andExpect(jsonPath("$.cathedraId", is(ID_1_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenSaveTeacherRequestedWithInvalidParameters() throws Exception {
        TeacherDtoRequest teacherDTORequest = new TeacherDtoRequest();
        teacherDTORequest.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDTORequest.setLastName(TEACHER_2_LAST_NAME_VALUE);
        teacherDTORequest.setPhone(TEACHER_1_PHONE_VALUE);
        teacherDTORequest.setQualification(QUALIFICATION_1_VALUE);
        teacherDTORequest.setCathedraId(ID_0_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(teacherDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldUpdateTeacherByIdInDb() throws Exception {
        TeacherDtoRequest teacherDTORequest = new TeacherDtoRequest();
        teacherDTORequest.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDTORequest.setLastName(TEACHER_2_LAST_NAME_VALUE);
        teacherDTORequest.setPhone(TEACHER_2_PHONE_VALUE);
        teacherDTORequest.setQualification(QUALIFICATION_1_VALUE);
        teacherDTORequest.setCathedraId(ID_1_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(teacherDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is(TEACHER_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.lastName", is(TEACHER_2_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.phone", is(TEACHER_2_PHONE_VALUE)))
            .andExpect(jsonPath("$.qualification", is(QUALIFICATION_1_VALUE)))
            .andExpect(jsonPath("$.cathedraId", is(ID_1_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenEditTeacherRequestedWithInvalidParameters() throws Exception {
        TeacherDtoRequest teacherDTORequest = new TeacherDtoRequest();
        teacherDTORequest.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDTORequest.setLastName(EMPTY_STRING);
        teacherDTORequest.setPhone(TEACHER_2_PHONE_VALUE);
        teacherDTORequest.setQualification(QUALIFICATION_1_VALUE);
        teacherDTORequest.setCathedraId(ID_1_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(teacherDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDeleteTeacherFromDb() throws Exception {
        mockMvc.perform(delete(DELETE_URL_TEMPLATE))
            .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenDeleteTeacherOnBadRequest() throws Exception {
        mockMvc.perform(delete(DELETE_INCORRECT_URL_TEMPLATE))
            .andExpect(status().is4xxClientError());
    }
}