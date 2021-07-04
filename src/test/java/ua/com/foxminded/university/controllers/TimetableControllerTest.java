package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.dto.TimetableDto;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.properties")
class TimetableControllerTest {
    private static final String GET_BY_ID_PROPERTY_NAME = "timetableDto";
    private static final String GET_BY_ID_URL_TEMPLATE = "/timetables/1";
    private static final String GET_BY_ID_VIEW_NAME = "/timetables/timetable-info";
    private static final String GET_ALL_VIEW_NAME = "/timetables/timetables";

    private static final String GET_NEW_TIMETABLE_URL_TEMPLATE = "/timetables/new";
    private static final String GET_NEW_TIMETABLE_PROPERTY_NAME = "timetableDto";
    private static final String GET_NEW_TIMETABLE_PROPERTY_NAME_GROUPS = "groups";
    private static final String GET_NEW_TIMETABLE_PROPERTY_NAME_TEACHERS = "teachers";
    private static final String GET_NEW_TIMETABLE_VIEW_NAME = "/timetables/timetable-new";
    private static final String POST_NEW_TIMETABLE_URL_TEMPLATE = "/timetables/";

    private static final String GET_EDIT_TIMETABLE_URL_TEMPLATE = "/timetables/1/edit";
    private static final String GET_EDIT_TIMETABLE_PROPERTY_NAME = "timetableDto";
    private static final String GET_EDIT_TIMETABLE_PROPERTY_NAME_GROUP = "groups";
    private static final String GET_EDIT_TIMETABLE_PROPERTY_NAME_TEACHER = "teachers";
    private static final String GET_EDIT_TIMETABLE_VIEW_NAME = "/timetables/timetable-update";
    private static final String PATCH_EDIT_TIMETABLE_URL_TEMPLATE = "/timetables/1";

    private static final String DELETE_TIMETABLE_VIEW_NAME = "/timetables/1";

    public static final String TEACHER_ID = "teacherId";
    public static final String GROUP_ID = "groupId";


    @Autowired
    public MockMvc mockMvc;

    @Test
    void shouldReturnCorrectedTimetablesPageWhenGetTimetablesPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTimetablesPageWhenGetNewTimetablePage() throws Exception {
        mockMvc.perform(get(GET_NEW_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTimetablesPageWhenGetEditTimetablePage() throws Exception {
        mockMvc.perform(get(GET_EDIT_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTimetablesPageWhenPostNewTimetablePage() throws Exception {
        mockMvc.perform(post(POST_NEW_TIMETABLE_URL_TEMPLATE)
            .param(DATE, DATE_1_VALUE_STRING)
            .param(START_TIME, TIME_1_STRING_VALUE)
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTimetablesPageWhenPostNewTimetablePageWithIncorrectParameters() throws Exception {
        mockMvc.perform(post(POST_NEW_TIMETABLE_URL_TEMPLATE)
            .param(DATE, EMPTY_STRING)
            .param(START_TIME, EMPTY_STRING)
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTimetablesPageWhenDeleteTimetablePage() throws Exception {
        mockMvc.perform(delete(DELETE_TIMETABLE_VIEW_NAME))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenPatchTimetablePage() throws Exception {
        mockMvc.perform(patch(PATCH_EDIT_TIMETABLE_URL_TEMPLATE)
            .param(DATE, DATE_1_VALUE_STRING)
            .param(START_TIME, TIME_1_STRING_VALUE)
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenPatchTimetablePageWithIncorrectParameters() throws Exception {
        mockMvc.perform(patch(PATCH_EDIT_TIMETABLE_URL_TEMPLATE)
            .param(DATE, EMPTY_STRING)
            .param(START_TIME, EMPTY_STRING)
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetTimetablePageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }


    @Test
    void shouldCheckForAttributeExistenceWhenGetNewTimetablePage() throws Exception {
        mockMvc.perform(get(GET_NEW_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_NEW_TIMETABLE_PROPERTY_NAME))
            .andExpect(model().attributeExists(GET_NEW_TIMETABLE_PROPERTY_NAME_GROUPS))
            .andExpect(model().attributeExists(GET_NEW_TIMETABLE_PROPERTY_NAME_TEACHERS))
            .andExpect(view().name(GET_NEW_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenPostNewTimetablePage() throws Exception {
        mockMvc.perform(post(POST_NEW_TIMETABLE_URL_TEMPLATE)
            .param(DATE, EMPTY_STRING)
            .param(START_TIME, EMPTY_STRING)
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().isOk());
    }

    @Test
    void shouldCheckForAttributeExistenceWhenPatchTimetablePage() throws Exception {
        mockMvc.perform(patch(PATCH_EDIT_TIMETABLE_URL_TEMPLATE)
            .param(DATE, DATE_1_VALUE_STRING)
            .param(START_TIME, TIME_1_STRING_VALUE)
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().isOk());
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetEditTimetablePage() throws Exception {
        mockMvc.perform(get(GET_EDIT_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_EDIT_TIMETABLE_PROPERTY_NAME))
            .andExpect(model().attributeExists(GET_EDIT_TIMETABLE_PROPERTY_NAME_GROUP))
            .andExpect(model().attributeExists(GET_EDIT_TIMETABLE_PROPERTY_NAME_TEACHER))
            .andExpect(view().name(GET_EDIT_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTimetableAttributesWhenGetDayTimetableById() throws Exception {
        TimetableDto timetableDto = new TimetableDto();
        timetableDto.setId(ID_1_VALUE);
        timetableDto.setDate(DATE_1_VALUE);
        timetableDto.setStartTime(TIME_1_VALUE);
        timetableDto.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDto.setSubject(SUBJECT_1_VALUE);
        timetableDto.setGroupId(ID_1_VALUE);
        timetableDto.setGroupName(GROUP_1_NAME_VALUE);
        timetableDto.setTeacherId(ID_1_VALUE);
        timetableDto.setTeacherFirstName(TEACHER_1_FIRST_NAME_VALUE);
        timetableDto.setTeacherLastName(TEACHER_1_LAST_NAME_VALUE);

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(timetableDto)));
    }
}