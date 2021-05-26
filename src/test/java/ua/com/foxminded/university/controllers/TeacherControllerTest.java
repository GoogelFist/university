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
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Teacher;

import javax.sql.DataSource;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebTestConfig.class})
@WebAppConfiguration
class TeacherControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/teachers/";
    private static final String GET_ALL_VIEW_NAME = "/teachers/teachers";
    private static final String GET_ALL_PROPERTY_NAME = "teachers";

    private static final String GET_BY_CATHEDRA_ID_URL_TEMPLATE = "/teachers/by-cathedra/1";
    private static final String GET_BY_CATHEDRA_ID_VIEW_NAME = "/teachers/teachers-by-cathedra-id";
    private static final String GET_BY_CATHEDRA_ID_PROPERTY_NAME = "teachersByCathedraId";

    private static final String GET_BY_ID_URL_TEMPLATE = "/teachers/1";
    private static final String GET_BY_ID_VIEW_NAME = "/teachers/teacher-info";
    private static final String GET_BY_ID_PROPERTY_NAME = "teacher";

    private static final String GET_NEW_TEACHER_URL_TEMPLATE = "/teachers/new";
    private static final String GET_NEW_TEACHER_PROPERTY_NAME = "teacher";
    private static final String GET_NEW_TEACHER_CATHEDRAS_PROPERTY_NAME = "cathedras";
    private static final String GET_NEW_TEACHER_VIEW_NAME = "/teachers/new-teacher";
    private static final String POST_NEW_TEACHER_URL_TEMPLATE = "/teachers/";

    private static final String GET_EDIT_TEACHER_URL_TEMPLATE = "/teachers/1/edit";
    private static final String GET_EDIT_TEACHER_PROPERTY_NAME = "teacher";
    private static final String GET_EDIT_TEACHER_VIEW_NAME = "/teachers/teacher-update";
    private static final String POST_EDIT_TEACHER_URL_TEMPLATE = "/teachers/1";

    private static final String DELETE_TEACHER_VIEW_NAME = "/teachers/1";

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
    void shouldReturnCorrectedTeachersPageWhenGetTeachersPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTeachersPageWhenGetTeachersPageByCathedraId() throws Exception {
        mockMvc.perform(get(GET_BY_CATHEDRA_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_CATHEDRA_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTeachersPageWhenGetTeachersPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTeachersPageWhenGetNewTeacherPage() throws Exception {
        mockMvc.perform(get(GET_NEW_TEACHER_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_TEACHER_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTeachersPageWhenGetEditTeacherPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_TEACHER_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_TEACHER_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTeachersPageWhenCreateNewTeacherPage() throws Exception {
        mockMvc.perform(post(POST_NEW_TEACHER_URL_TEMPLATE)
            .param(FIRST_NAME, TEACHER_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, TEACHER_1_LAST_NAME_VALUE)
            .param(PHONE, TEACHER_1_PHONE_VALUE)
            .param(QUALIFICATION, QUALIFICATION_1_VALUE)
            .param(CATHEDRA_ID, valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, TEACHERS)));
    }

    @Test
    void shouldReturnCorrectedTeachersPageWhenEditTeacherPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_TEACHER_URL_TEMPLATE)
            .param(FIRST_NAME, TEACHER_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, TEACHER_1_LAST_NAME_VALUE)
            .param(PHONE, TEACHER_1_PHONE_VALUE)
            .param(QUALIFICATION, QUALIFICATION_1_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, TEACHERS)));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenDeleteStudentsPage() throws Exception {
        mockMvc.perform(delete(DELETE_TEACHER_VIEW_NAME))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, TEACHERS)));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetTeachersPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_ALL_PROPERTY_NAME))
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetTeachersPageByCathedraId() throws Exception {
        mockMvc.perform(get(GET_BY_CATHEDRA_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_CATHEDRA_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_CATHEDRA_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetTeachersPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetNewTeacherPage() throws Exception {
        mockMvc.perform(get(GET_NEW_TEACHER_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_NEW_TEACHER_PROPERTY_NAME))
            .andExpect(model().attributeExists(GET_NEW_TEACHER_CATHEDRAS_PROPERTY_NAME))
            .andExpect(view().name(GET_NEW_TEACHER_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenCreateNewTeacherPage() throws Exception {
        mockMvc.perform(post(POST_NEW_TEACHER_URL_TEMPLATE)
            .param(FIRST_NAME, TEACHER_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, TEACHER_1_LAST_NAME_VALUE)
            .param(PHONE, TEACHER_1_PHONE_VALUE)
            .param(QUALIFICATION, QUALIFICATION_1_VALUE)
            .param(CATHEDRA_ID, valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(TEACHER));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditTeacherPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_TEACHER_URL_TEMPLATE)
            .param(FIRST_NAME, TEACHER_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, TEACHER_1_LAST_NAME_VALUE)
            .param(PHONE, TEACHER_1_PHONE_VALUE)
            .param(QUALIFICATION, QUALIFICATION_1_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(TEACHER));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetEditTeacherPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_TEACHER_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_EDIT_TEACHER_PROPERTY_NAME))
            .andExpect(view().name(GET_EDIT_TEACHER_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedTeacherAttributesWhenGetTeachers() throws Exception {
        Cathedra physicsCathedra = new Cathedra(ID_1_VALUE, PHYSICS);
        Cathedra medicalsCathedra = new Cathedra(ID_2_VALUE, MEDICALS);

        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_2))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(FIRST_NAME, is(TEACHER_1_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(TEACHER_1_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(TEACHER_1_PHONE_VALUE)),
                    hasProperty(QUALIFICATION, is(QUALIFICATION_1_VALUE)),
                    hasProperty(CATHEDRA, is(physicsCathedra))
                ))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(FIRST_NAME, is(TEACHER_2_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(TEACHER_2_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(TEACHER_2_PHONE_VALUE)),
                    hasProperty(QUALIFICATION, is(QUALIFICATION_2_VALUE)),
                    hasProperty(CATHEDRA, is(medicalsCathedra))
                ))));
    }

    @Test
    void shouldReturnCorrectedTeacherAttributesWhenGetTeachersByCathedra() throws Exception {
        Cathedra physicsCathedra = new Cathedra(ID_1_VALUE, PHYSICS);

        mockMvc.perform(get(GET_BY_CATHEDRA_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_CATHEDRA_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_CATHEDRA_ID_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(FIRST_NAME, is(TEACHER_1_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(TEACHER_1_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(TEACHER_1_PHONE_VALUE)),
                    hasProperty(QUALIFICATION, is(QUALIFICATION_1_VALUE)),
                    hasProperty(CATHEDRA, is(physicsCathedra))
                ))));
    }

    @Test
    void shouldReturnCorrectedTeacherAttributesWhenGetTeacherById() throws Exception {
        Teacher teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE, PHYSICS));

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(teacher)));
    }
}