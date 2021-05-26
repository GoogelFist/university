package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.foxminded.university.entities.*;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebTestConfig.class})
@WebAppConfiguration
class DayTimetableControllerTest {
    private static final String GET_BY_ID_PROPERTY_NAME = "dayTimetable";
    private static final String GET_BY_ID_URL_TEMPLATE = "/day-timetables/1";
    private static final String GET_BY_ID_VIEW_NAME = "/daytimetables/day-timetable-info";

    private static final String GET_BY_MONTH_TIMETABLE_ID_URL_TEMPLATE = "/day-timetables/by-month-timetable/1";
    private static final String GET_BY_MONTH_TIMETABLE_ID_VIEW_NAME = "/daytimetables/day-timetables-by-month-timetable";
    private static final String GET_BY_MONTH_TIMETABLE_ID_PROPERTY_NAME = "dayTimetablesByMonthTimetable";

    private static final String GET_BY_GROUP_ID_URL_TEMPLATE = "/day-timetables/by-group/1";
    private static final String GET_BY_GROUP_ID_VIEW_NAME = "/daytimetables/day-timetables-by-group";
    private static final String GET_BY_GROUP_ID_PROPERTY_NAME = "dayTimetablesByGroup";

    private static final String GET_BY_TEACHER_ID_URL_TEMPLATE = "/day-timetables/by-teacher/1";
    private static final String GET_BY_TEACHER_ID_VIEW_NAME = "/daytimetables/day-timetables-by-teacher";
    private static final String GET_BY_TEACHER_ID_PROPERTY_NAME = "dayTimetablesByTeacher";

    private static final String GET_NEW_TIMETABLE_URL_TEMPLATE = "/day-timetables/1/new";
    private static final String GET_NEW_TIMETABLE_PROPERTY_NAME = "dayTimetable";
    private static final String GET_NEW_TIMETABLE_PROPERTY_NAME_GROUPS = "groups";
    private static final String GET_NEW_TIMETABLE_PROPERTY_NAME_TEACHERS = "teachers";
    private static final String GET_NEW_TIMETABLE_VIEW_NAME = "/daytimetables/new-day-timetable";
    private static final String POST_NEW_TIMETABLE_URL_TEMPLATE = "/day-timetables/";

    private static final String GET_EDIT_TIMETABLE_URL_TEMPLATE = "/day-timetables/1/edit";
    private static final String GET_EDIT_TIMETABLE_PROPERTY_NAME = "dayTimetable";
    private static final String GET_EDIT_TIMETABLE_PROPERTY_NAME_GROUP = "groups";
    private static final String GET_EDIT_TIMETABLE_PROPERTY_NAME_TEACHER = "teachers";
    private static final String GET_EDIT_TIMETABLE_VIEW_NAME = "/daytimetables/day-timetable-update";
    private static final String POST_EDIT_TIMETABLE_URL_TEMPLATE = "/day-timetables/1";

    private static final String DELETE_TIMETABLE_VIEW_NAME = "/day-timetables/1";
    private static final String MONTH_TIMETABLES_VIEW_NAME = "month-timetables";

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenGetDayTimetablesPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenGetDayTimetablesPageByMonthTimetableId() throws Exception {
        mockMvc.perform(get(GET_BY_MONTH_TIMETABLE_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_MONTH_TIMETABLE_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenGetDayTimetablesPageByGroupId() throws Exception {
        mockMvc.perform(get(GET_BY_GROUP_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_GROUP_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenGetDayTimetablesPageByTeacherId() throws Exception {
        mockMvc.perform(get(GET_BY_TEACHER_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_TEACHER_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenGetNewTimetablePage() throws Exception {
        mockMvc.perform(get(GET_NEW_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenGetEditTimetablePage() throws Exception {
        mockMvc.perform(get(GET_EDIT_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenCreateNewTimetablePage() throws Exception {
        mockMvc.perform(post(POST_NEW_TIMETABLE_URL_TEMPLATE)
            .param(START_TIME, String.valueOf(DAY_TIMETABLE_1_TIME_VALUE))
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE))
            .param(MONTH_TIMETABLE_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME)));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenDeleteTimetablePage() throws Exception {
        mockMvc.perform(delete(DELETE_TIMETABLE_VIEW_NAME))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME)));
    }

    @Test
    void shouldReturnCorrectedDayTimetablesPageWhenEditTimetablePage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_TIMETABLE_URL_TEMPLATE)
            .param(START_TIME, String.valueOf(DAY_TIMETABLE_1_TIME_VALUE))
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE))
            .param(MONTH_TIMETABLE_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME)));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetDayTimetablePageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetDayTimetablePageByMonthTimetableId() throws Exception {
        mockMvc.perform(get(GET_BY_MONTH_TIMETABLE_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_MONTH_TIMETABLE_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_MONTH_TIMETABLE_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetDayTimetablePageByGroupId() throws Exception {
        mockMvc.perform(get(GET_BY_GROUP_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_GROUP_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_GROUP_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetDayTimetablePageByTeacherId() throws Exception {
        mockMvc.perform(get(GET_BY_TEACHER_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_TEACHER_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_TEACHER_ID_VIEW_NAME));
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
    void shouldCheckForAttributeExistenceWhenCreateNewTimetablePage() throws Exception {
        mockMvc.perform(post(POST_NEW_TIMETABLE_URL_TEMPLATE)
            .param(START_TIME, String.valueOf(DAY_TIMETABLE_1_TIME_VALUE))
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE))
            .param(MONTH_TIMETABLE_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(DAY_TIMETABLE));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditTimetablePage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_TIMETABLE_URL_TEMPLATE)
            .param(START_TIME, String.valueOf(DAY_TIMETABLE_1_TIME_VALUE))
            .param(LECTURE_HALL, LECTURE_HALL_1_VALUE)
            .param(SUBJECT, SUBJECT_1_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE))
            .param(TEACHER_ID, String.valueOf(ID_1_VALUE))
            .param(MONTH_TIMETABLE_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(DAY_TIMETABLE));
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
    void shouldReturnCorrectedDayTimetableAttributesWhenGetDayTimetableById() throws Exception {
        List<Student> students = singletonList(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        Cathedra cathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, students, cathedra);
        Teacher teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, cathedra);
        MonthTimetable monthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1);
        DayTimetable dayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, monthTimetable);

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(dayTimetable)));
    }

    @Test
    void shouldReturnCorrectedDayTimetableAttributesWhenGetDayTimetableByMonthTimetableId() throws Exception {
        List<Student> students = singletonList(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        Cathedra cathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, students, cathedra);
        Teacher teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, cathedra);
        MonthTimetable monthTimetable = new MonthTimetable(ID_1_VALUE);

        mockMvc.perform(get(GET_BY_MONTH_TIMETABLE_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_MONTH_TIMETABLE_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_MONTH_TIMETABLE_ID_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(START_TIME, is(DAY_TIMETABLE_1_TIME_VALUE)),
                    hasProperty(LECTURE_HALL, is(LECTURE_HALL_1_VALUE)),
                    hasProperty(SUBJECT, is(SUBJECT_1_VALUE)),
                    hasProperty(GROUP, is(group)),
                    hasProperty(TEACHER, is(teacher)),
                    hasProperty(MONTH_TIMETABLE, is(monthTimetable))
                ))));
    }

    @Test
    void shouldReturnCorrectedDayTimetableAttributesWhenGetDayTimetableByGroupId() throws Exception {
        Group group = new Group(ID_1_VALUE);
        Cathedra cathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        Teacher teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, cathedra);
        MonthTimetable monthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1);

        mockMvc.perform(get(GET_BY_GROUP_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_GROUP_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_GROUP_ID_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(START_TIME, is(DAY_TIMETABLE_1_TIME_VALUE)),
                    hasProperty(LECTURE_HALL, is(LECTURE_HALL_1_VALUE)),
                    hasProperty(SUBJECT, is(SUBJECT_1_VALUE)),
                    hasProperty(GROUP, is(group)),
                    hasProperty(TEACHER, is(teacher)),
                    hasProperty(MONTH_TIMETABLE, is(monthTimetable))
                ))));
    }

    @Test
    void shouldReturnCorrectedDayTimetableAttributesWhenGetDayTimetableByTeacherId() throws Exception {
        List<Student> students = singletonList(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        Cathedra cathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, students, cathedra);
        Teacher teacher = new Teacher(ID_1_VALUE);
        MonthTimetable monthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1);

        mockMvc.perform(get(GET_BY_TEACHER_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_TEACHER_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_TEACHER_ID_PROPERTY_NAME, hasItem(
                allOf(hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(START_TIME, is(DAY_TIMETABLE_1_TIME_VALUE)),
                    hasProperty(LECTURE_HALL, is(LECTURE_HALL_1_VALUE)),
                    hasProperty(SUBJECT, is(SUBJECT_1_VALUE)),
                    hasProperty(GROUP, is(group)),
                    hasProperty(TEACHER, is(teacher)),
                    hasProperty(MONTH_TIMETABLE, is(monthTimetable))
                ))));
    }
}