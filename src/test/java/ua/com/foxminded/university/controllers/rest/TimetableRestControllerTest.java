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
import ua.com.foxminded.university.entities.dto.TimetableDtoRequest;

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
class TimetableRestControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/api/timetables";
    private static final String GET_ALL_INCORRECT_URL_TEMPLATE = "/api/timetablssss";
    private static final String GET_BY_ID_URL_TEMPLATE = "/api/timetables/1";
    private static final String GET_BY_ID_INCORRECT_URL_TEMPLATE = "/api/timetablssss/5";

    private static final String POST_URL_TEMPLATE = "/api/timetables";
    private static final String PATCH_URL_TEMPLATE = "/api/timetables/1";
    private static final String DELETE_URL_TEMPLATE = "/api/timetables/2";
    private static final String DELETE_INCORRECT_URL_TEMPLATE = "/api/timetablsss/incorrect";


    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRetrieveAllTimetablesFromDbWhensGetAllTimetables() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", is(JSON_SIZE_VALUE_2)))
            .andExpect(jsonPath("$.[0].id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].date", is(DATE_1_VALUE_STRING)))
            .andExpect(jsonPath("$.[0].startTime", is(TIME_1_STRING_VALUE)))
            .andExpect(jsonPath("$.[0].lectureHall", is(LECTURE_HALL_1_VALUE)))
            .andExpect(jsonPath("$.[0].subject", is(SUBJECT_1_VALUE)))
            .andExpect(jsonPath("$.[0].groupId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].groupName", is(GROUP_1_NAME_VALUE)))
            .andExpect(jsonPath("$.[0].teacherId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.[0].teacherFirstName", is(TEACHER_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.[0].teacherLastName", is(TEACHER_1_LAST_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].id", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].date", is(DATE_2_VALUE_STRING)))
            .andExpect(jsonPath("$.[1].startTime", is(TIME_2_STRING_VALUE)))
            .andExpect(jsonPath("$.[1].lectureHall", is(LECTURE_HALL_2_VALUE)))
            .andExpect(jsonPath("$.[1].subject", is(SUBJECT_2_VALUE)))
            .andExpect(jsonPath("$.[1].groupId", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].groupName", is(GROUP_2_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].teacherId", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.[1].teacherFirstName", is(TEACHER_2_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.[1].teacherLastName", is(TEACHER_2_LAST_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionOnBadRequestWhenGetAllTimetables() throws Exception {
        mockMvc.perform(get(GET_ALL_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldRetrieveTimetableFromDbWhenGetTimetableByID() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.date", is(DATE_1_VALUE_STRING)))
            .andExpect(jsonPath("$.startTime", is(TIME_1_STRING_VALUE)))
            .andExpect(jsonPath("$.lectureHall", is(LECTURE_HALL_1_VALUE)))
            .andExpect(jsonPath("$.subject", is(SUBJECT_1_VALUE)))
            .andExpect(jsonPath("$.groupId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.groupName", is(GROUP_1_NAME_VALUE)))
            .andExpect(jsonPath("$.teacherId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.teacherFirstName", is(TEACHER_1_FIRST_NAME_VALUE)))
            .andExpect(jsonPath("$.teacherLastName", is(TEACHER_1_LAST_NAME_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenGetTimetableById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_INCORRECT_URL_TEMPLATE)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldAddNewTimetableToDb() throws Exception {
        TimetableDtoRequest timetableDtoRequest = new TimetableDtoRequest();
        timetableDtoRequest.setDate(DATE_1_VALUE);
        timetableDtoRequest.setStartTime(TIME_1_VALUE);
        timetableDtoRequest.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDtoRequest.setSubject(SUBJECT_1_VALUE);
        timetableDtoRequest.setGroupId(ID_1_VALUE);
        timetableDtoRequest.setTeacherId(ID_1_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(timetableDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.date", is(DATE_1_VALUE_STRING)))
            .andExpect(jsonPath("$.startTime", is(TIME_1_STRING_VALUE)))
            .andExpect(jsonPath("$.lectureHall", is(LECTURE_HALL_1_VALUE)))
            .andExpect(jsonPath("$.subject", is(SUBJECT_1_VALUE)))
            .andExpect(jsonPath("$.groupId", is(ID_1_VALUE)))
            .andExpect(jsonPath("$.teacherId", is(ID_1_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenSaveTimetableRequestedWithInvalidParameters() throws Exception {
        TimetableDtoRequest timetableDtoRequest = new TimetableDtoRequest();
        timetableDtoRequest.setDate(DATE_1_VALUE);
        timetableDtoRequest.setStartTime(TIME_1_VALUE);
        timetableDtoRequest.setLectureHall(EMPTY_STRING);
        timetableDtoRequest.setSubject(SUBJECT_1_VALUE);
        timetableDtoRequest.setGroupId(ID_1_VALUE);
        timetableDtoRequest.setTeacherId(ID_1_VALUE);

        mockMvc.perform(post(POST_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(timetableDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldUpdateTimetableByIdInDb() throws Exception {
        TimetableDtoRequest timetableDtoRequest = new TimetableDtoRequest();
        timetableDtoRequest.setDate(DATE_1_VALUE);
        timetableDtoRequest.setStartTime(TIME_1_VALUE);
        timetableDtoRequest.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDtoRequest.setSubject(SUBJECT_2_VALUE);
        timetableDtoRequest.setGroupId(ID_2_VALUE);
        timetableDtoRequest.setTeacherId(ID_1_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(timetableDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.date", is(DATE_1_VALUE_STRING)))
            .andExpect(jsonPath("$.startTime", is(TIME_1_STRING_VALUE)))
            .andExpect(jsonPath("$.lectureHall", is(LECTURE_HALL_1_VALUE)))
            .andExpect(jsonPath("$.subject", is(SUBJECT_2_VALUE)))
            .andExpect(jsonPath("$.groupId", is(ID_2_VALUE)))
            .andExpect(jsonPath("$.teacherId", is(ID_1_VALUE)));
    }

    @Test
    void shouldThrowExceptionWhenEditTimetableRequestedWithInvalidParameters() throws Exception {
        TimetableDtoRequest timetableDtoRequest = new TimetableDtoRequest();
        timetableDtoRequest.setDate(DATE_1_VALUE);
        timetableDtoRequest.setStartTime(TIME_1_VALUE);
        timetableDtoRequest.setLectureHall(EMPTY_STRING);
        timetableDtoRequest.setSubject(SUBJECT_1_VALUE);
        timetableDtoRequest.setGroupId(ID_1_VALUE);
        timetableDtoRequest.setTeacherId(ID_1_VALUE);

        mockMvc.perform(patch(PATCH_URL_TEMPLATE)
            .content(objectMapper.writeValueAsString(timetableDtoRequest))
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDeleteTimetableFromDb() throws Exception {
        mockMvc.perform(delete(DELETE_URL_TEMPLATE))
            .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenDeleteTimetableOnBadRequest() throws Exception {
        mockMvc.perform(delete(DELETE_INCORRECT_URL_TEMPLATE))
            .andExpect(status().is4xxClientError());
    }
}