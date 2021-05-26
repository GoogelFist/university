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
class MonthTimetableControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/month-timetables/";
    private static final String GET_ALL_VIEW_NAME = "/monthtimetables/month-timetables";
    private static final String GET_ALL_PROPERTY_NAME = "monthTimetables";

    private static final String GET_BY_ID_URL_TEMPLATE = "/month-timetables/1";
    private static final String GET_BY_ID_VIEW_NAME = "/monthtimetables/month-timetable-info";
    private static final String GET_BY_ID_PROPERTY_NAME = "monthTimetable";

    private static final String GET_NEW_TIMETABLE_URL_TEMPLATE = "/month-timetables/new";
    private static final String GET_NEW_TIMETABLE_PROPERTY_NAME = "monthTimetable";
    private static final String GET_NEW_TIMETABLE_VIEW_NAME = "/monthtimetables/new-month-timetable";
    private static final String POST_NEW_TIMETABLE_URL_TEMPLATE = "/month-timetables/";

    private static final String GET_EDIT_TIMETABLE_URL_TEMPLATE = "/month-timetables/1/edit";
    private static final String GET_EDIT_TIMETABLE_PROPERTY_NAME = "monthTimetable";
    private static final String GET_EDIT_TIMETABLE_VIEW_NAME = "/monthtimetables/month-timetable-update";
    private static final String POST_EDIT_TIMETABLE_URL_TEMPLATE = "/month-timetables/1";

    private static final String DELETE_MONTH_TIMETABLE_VIEW_NAME = "/month-timetables/1";
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
    void shouldReturnCorrectedMonthTimetablesPageWhenGetMonthTimetablesPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedMonthTimetablesPageWhenGetMonthTimetablesPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedMonthTimetablesPageWhenGetNewTimetablePage() throws Exception {
        mockMvc.perform(get(GET_NEW_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedMonthTimetablesPageWhenGetEditTimetablePage() throws Exception {
        mockMvc.perform(get(GET_EDIT_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedMonthTimetablesPageWhenCreateNewTimetablePage() throws Exception {
        mockMvc.perform(post(POST_NEW_TIMETABLE_URL_TEMPLATE)
            .param(DATE, String.valueOf(MONTH_TIMETABLE_DATE_VALUE_1)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME)));
    }

    @Test
    void shouldReturnCorrectedMonthTimetablesPageWhenDeleteTimetablePage() throws Exception {
        mockMvc.perform(delete(DELETE_MONTH_TIMETABLE_VIEW_NAME))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME)));
    }

    @Test
    void shouldReturnCorrectedMonthTimetablesPageWhenEditTimetablePage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_TIMETABLE_URL_TEMPLATE)
            .param(DATE, String.valueOf(MONTH_TIMETABLE_DATE_VALUE_1)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME)));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetMonthTimetablesPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_ALL_PROPERTY_NAME))
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetMonthTimetablesPageById() throws Exception {
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
            .andExpect(view().name(GET_NEW_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenCreateNewTimetablePage() throws Exception {
        mockMvc.perform(post(POST_NEW_TIMETABLE_URL_TEMPLATE)
            .param(DATE, String.valueOf(MONTH_TIMETABLE_DATE_VALUE_1)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(MONTH_TIMETABLE));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditTimetablePage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_TIMETABLE_URL_TEMPLATE)
            .param(DATE, String.valueOf(MONTH_TIMETABLE_DATE_VALUE_1)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(MONTH_TIMETABLE));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetEditTimetablePage() throws Exception {
        mockMvc.perform(get(GET_EDIT_TIMETABLE_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_EDIT_TIMETABLE_PROPERTY_NAME))
            .andExpect(view().name(GET_EDIT_TIMETABLE_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetMonthTimetables() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_2))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(DATE, is(MONTH_TIMETABLE_DATE_VALUE_1))
                ))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(DATE, is(MONTH_TIMETABLE_DATE_VALUE_2))
                ))));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetMonthTimetablesById() throws Exception {
        List<Student> students = singletonList(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        Cathedra cathedra = new Cathedra(ID_1_VALUE, PHYSICS);
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, students, cathedra);
        Teacher teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, cathedra);
        List<DayTimetable> dayTimetables = singletonList(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, new MonthTimetable(ID_1_VALUE)));
        MonthTimetable monthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1, dayTimetables);

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(monthTimetable)));
    }
}