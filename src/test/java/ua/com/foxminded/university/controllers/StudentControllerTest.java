package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebTestConfig.class})
@WebAppConfiguration
class StudentControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/students/";
    private static final String GET_ALL_VIEW_NAME = "/students/students";
    private static final String GET_ALL_PROPERTY_NAME = "students";

    private static final String GET_BY_GROUP_ID_URL_TEMPLATE = "/students/by-group/1";
    private static final String GET_BY_GROUP_ID_VIEW_NAME = "/students/students-by-group-id";
    private static final String GET_BY_GROUP_ID_PROPERTY_NAME = "studentsByGroupId";

    private static final String GET_BY_ID_URL_TEMPLATE = "/students/1";
    private static final String GET_BY_ID_VIEW_NAME = "/students/student-info";
    private static final String GET_BY_ID_PROPERTY_NAME = "student";

    private static final String GET_NEW_STUDENT_URL_TEMPLATE = "/students/new";
    private static final String GET_NEW_STUDENT_PROPERTY_NAME = "student";
    private static final String GET_NEW_STUDENT_GROUPS_PROPERTY_NAME = "groups";
    private static final String GET_NEW_STUDENT_VIEW_NAME = "/students/new-student";
    private static final String POST_NEW_STUDENT_URL_TEMPLATE = "/students/";

    private static final String GET_EDIT_STUDENT_URL_TEMPLATE = "/students/1/edit";
    private static final String GET_EDIT_STUDENT_PROPERTY_NAME = "student";
    private static final String GET_EDIT_STUDENT_VIEW_NAME = "/students/student-update";
    private static final String POST_EDIT_STUDENT_URL_TEMPLATE = "/students/1";

    private static final String DELETE_STUDENT_VIEW_NAME = "/students/1";

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
    void shouldReturnCorrectedStudentsPageWhenGetStudentsPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenGetStudentsPageByGroupId() throws Exception {
        mockMvc.perform(get(GET_BY_GROUP_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_GROUP_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenGetStudentsPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenGetNewStudentsPage() throws Exception {
        mockMvc.perform(get(GET_NEW_STUDENT_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_STUDENT_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenGetEditStudentsPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_STUDENT_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_STUDENT_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenCreateNewStudentsPage() throws Exception {
        mockMvc.perform(post(POST_NEW_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, STUDENT_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, STUDENT_1_LAST_NAME_VALUE)
            .param(PHONE, STUDENT_1_PHONE_VALUE)
            .param(GROUP_ID, valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, STUDENTS)));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenEditStudentsPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, STUDENT_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, STUDENT_1_LAST_NAME_VALUE)
            .param(PHONE, STUDENT_1_PHONE_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, STUDENTS)));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenDeleteStudentsPage() throws Exception {
        mockMvc.perform(delete(DELETE_STUDENT_VIEW_NAME))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, STUDENTS)));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetStudentsPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_ALL_PROPERTY_NAME))
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetStudentsPageByGroupId() throws Exception {
        mockMvc.perform(get(GET_BY_GROUP_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_GROUP_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_GROUP_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetStudentPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetNewStudentPage() throws Exception {
        mockMvc.perform(get(GET_NEW_STUDENT_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_NEW_STUDENT_PROPERTY_NAME))
            .andExpect(model().attributeExists(GET_NEW_STUDENT_GROUPS_PROPERTY_NAME))
            .andExpect(view().name(GET_NEW_STUDENT_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenCreateNewStudentPage() throws Exception {
        mockMvc.perform(post(POST_NEW_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, STUDENT_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, STUDENT_1_LAST_NAME_VALUE)
            .param(PHONE, STUDENT_1_PHONE_VALUE)
            .param(GROUP_ID, valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(STUDENT));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditStudentPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, STUDENT_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, STUDENT_1_LAST_NAME_VALUE)
            .param(PHONE, STUDENT_1_PHONE_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(STUDENT));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetEditStudentPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_STUDENT_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_EDIT_STUDENT_PROPERTY_NAME))
            .andExpect(view().name(GET_EDIT_STUDENT_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetStudents() throws Exception {
        Group group1 = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE));
        Group group2 = new Group(ID_2_VALUE, GROUP_2_NAME_VALUE, new Cathedra(ID_2_VALUE));

        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_2))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(FIRST_NAME, is(STUDENT_1_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(STUDENT_1_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(STUDENT_1_PHONE_VALUE)),
                    hasProperty(GROUP, is(group1))
                ))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(FIRST_NAME, is(STUDENT_2_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(STUDENT_2_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(STUDENT_2_PHONE_VALUE)),
                    hasProperty(GROUP, is(group2))
                ))));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetStudentsByGroupId() throws Exception {
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE));

        mockMvc.perform(get(GET_BY_GROUP_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_GROUP_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_GROUP_ID_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(FIRST_NAME, is(STUDENT_1_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(STUDENT_1_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(STUDENT_1_PHONE_VALUE)),
                    hasProperty(GROUP, is(group))
                ))));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetStudentById() throws Exception {
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE));
        Student student = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, group);

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(student)));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetNewStudent() throws Exception {
        Cathedra cathedra1 = new Cathedra(ID_1_VALUE);
        Cathedra cathedra2 = new Cathedra(ID_2_VALUE);
        List<Student> students1 = singletonList(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        List<Student> students2 = singletonList(new Student(ID_2_VALUE, STUDENT_2_FIRST_NAME_VALUE, STUDENT_2_LAST_NAME_VALUE, STUDENT_2_PHONE_VALUE, new Group(ID_2_VALUE)));
        Student emptyStudent = new Student(ID_0_VALUE, null, null, null, new Group());

        mockMvc.perform(get(GET_NEW_STUDENT_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_NEW_STUDENT_GROUPS_PROPERTY_NAME, hasSize(INT_VALUE_2)))
            .andExpect(model().attribute(GET_NEW_STUDENT_GROUPS_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(NAME, is(GROUP_1_NAME_VALUE)),
                    hasProperty(STUDENTS, is(students1)),
                    hasProperty(CATHEDRA, is(cathedra1))
                ))))
            .andExpect(model().attribute(GET_NEW_STUDENT_GROUPS_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(NAME, is(GROUP_2_NAME_VALUE)),
                    hasProperty(STUDENTS, is(students2)),
                    hasProperty(CATHEDRA, is(cathedra2))
                ))))
            .andExpect(model().attribute(GET_NEW_STUDENT_PROPERTY_NAME, equalTo(emptyStudent)));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetEditStudent() throws Exception {
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE));
        Student student = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, group);

        mockMvc.perform(get(GET_EDIT_STUDENT_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_EDIT_STUDENT_PROPERTY_NAME, equalTo(student)));
    }
}