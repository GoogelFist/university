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
import ua.com.foxminded.university.entities.dto.StudentDtoRequest;

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
class StudentRestControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/api/students";
    private static final String GET_ALL_INCORRECT_URL_TEMPLATE = "/api/studentssss";
    private static final String GET_BY_ID_URL_TEMPLATE = "/api/students/1";
    private static final String GET_BY_ID_INCORRECT_URL_TEMPLATE = "/api/studentssss/5";

    private static final String POST_URL_TEMPLATE = "/api/students";
    private static final String PATCH_URL_TEMPLATE = "/api/students/1";
    private static final String DELETE_URL_TEMPLATE = "/api/students/2";
    private static final String DELETE_INCORRECT_URL_TEMPLATE = "/api/studentsss/incorrect";


    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRetrieveAllStudentsFromDbWhensGetAllStudents() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", is(JSON_SIZE_VALUE_2)))
            .andExpect(jsonPath("$.[0].id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].firstName", is(STUDENT_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.[0].lastName", is(STUDENT_1_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.[0].phone", is(STUDENT_1_PHONE_VALUE)))
            .andExpect(jsonPath("$.[0].groupId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].groupName", is(GROUP_1_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].id", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].firstName", is(STUDENT_2_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].lastName", is(STUDENT_2_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].phone", is(STUDENT_2_PHONE_VALUE)))
            .andExpect(jsonPath("$.[1].groupId", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].groupName", is(GROUP_2_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionOnBadRequestWhenGetAllStudents() throws Exception {
        mockMvc.perform(get(GET_ALL_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldRetrieveStudentFromDbWhenGetStudentByID() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.firstName", is(STUDENT_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.lastName", is(STUDENT_1_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.phone", is(STUDENT_1_PHONE_VALUE)))
            .andExpect(jsonPath("$.groupId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.groupName", is(GROUP_1_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenGetStudentById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldAddNewStudentToDb() throws Exception {
        StudentDtoRequest studentDTORequest = new StudentDtoRequest();
        studentDTORequest.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDTORequest.setLastName(STUDENT_2_LAST_NAME_VALUE);
        studentDTORequest.setPhone(STUDENT_1_PHONE_VALUE);
        studentDTORequest.setGroupId(ID_1_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(studentDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is(STUDENT_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.lastName", is(STUDENT_2_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.phone", is(STUDENT_1_PHONE_VALUE)))
            .andExpect(jsonPath("$.groupId", is(ID_1_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenSaveStudentRequestedWithInvalidParameters() throws Exception {
        StudentDtoRequest studentDTORequest = new StudentDtoRequest();
        studentDTORequest.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDTORequest.setLastName(STUDENT_1_LAST_NAME_VALUE);
        studentDTORequest.setPhone(STUDENT_1_PHONE_VALUE);
        studentDTORequest.setGroupId(ID_0_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(studentDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldUpdateStudentByIdInDb() throws Exception {
        StudentDtoRequest studentDTORequest = new StudentDtoRequest();
        studentDTORequest.setFirstName(STUDENT_2_FIRST_NAME_VALUE);
        studentDTORequest.setLastName(STUDENT_1_LAST_NAME_VALUE);
        studentDTORequest.setPhone(STUDENT_1_PHONE_VALUE);
        studentDTORequest.setGroupId(ID_2_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(studentDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is(STUDENT_2_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.lastName", is(STUDENT_1_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.phone", is(STUDENT_1_PHONE_VALUE)))
            .andExpect(jsonPath("$.groupId", is(ID_2_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenEditStudentRequestedWithInvalidParameters() throws Exception {
        StudentDtoRequest studentDTORequest = new StudentDtoRequest();
        studentDTORequest.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDTORequest.setLastName(EMPTY_STRING);
        studentDTORequest.setPhone(STUDENT_1_PHONE_VALUE);
        studentDTORequest.setGroupId(ID_1_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(studentDTORequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDeleteStudentFromDb() throws Exception {
        mockMvc.perform(delete(DELETE_URL_TEMPLATE))
            .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenDeleteStudentOnBadRequest() throws Exception {
        mockMvc.perform(delete(DELETE_INCORRECT_URL_TEMPLATE))
            .andExpect(status().is4xxClientError());
    }
}