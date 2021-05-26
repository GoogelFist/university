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

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebTestConfig.class})
@WebAppConfiguration
class GroupControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/groups/";
    private static final String GET_ALL_VIEW_NAME = "/groups/groups";
    private static final String GET_ALL_PROPERTY_NAME = "groups";

    private static final String GET_BY_CATHEDRA_ID_URL_TEMPLATE = "/groups/by-cathedra/1";
    private static final String GET_BY_CATHEDRA_ID_VIEW_NAME = "/groups/groups-by-cathedra-id";
    private static final String GET_BY_CATHEDRA_ID_PROPERTY_NAME = "groupsByCathedraId";

    private static final String GET_BY_ID_URL_TEMPLATE = "/groups/1";
    private static final String GET_BY_ID_VIEW_NAME = "/groups/group-info";
    private static final String GET_BY_ID_PROPERTY_NAME = "group";

    private static final String GET_NEW_GROUP_URL_TEMPLATE = "/groups/new";
    private static final String GET_NEW_GROUP_PROPERTY_NAME = "group";
    private static final String GET_NEW_GROUP_CATHEDRAS_PROPERTY_NAME = "cathedras";
    private static final String GET_NEW_GROUP_VIEW_NAME = "/groups/new-group";
    private static final String POST_NEW_GROUP_URL_TEMPLATE = "/groups/";

    private static final String GET_EDIT_GROUP_URL_TEMPLATE = "/groups/1/edit";
    private static final String GET_EDIT_GROUP_PROPERTY_NAME = "group";
    private static final String GET_EDIT_GROUP_VIEW_NAME = "/groups/group-update";
    private static final String POST_EDIT_GROUP_URL_TEMPLATE = "/groups/1";

    private static final String DELETE_GROUP_VIEW_NAME = "/groups/1";

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
    void shouldReturnCorrectedGroupsPageWhenGetGroupsPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedGroupsPageWhenGetGroupsPageByCathedraId() throws Exception {
        mockMvc.perform(get(GET_BY_CATHEDRA_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_CATHEDRA_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedGroupsPageWhenGetGroupPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedGroupsPageWhenGetNewGroupPage() throws Exception {
        mockMvc.perform(get(GET_NEW_GROUP_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_GROUP_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedGroupsPageWhenGetEditGroupPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_GROUP_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_GROUP_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedGroupPageWhenCreateNewGroupsPage() throws Exception {
        mockMvc.perform(post(POST_NEW_GROUP_URL_TEMPLATE)
            .param(NAME, GROUP_1_NAME_VALUE)
            .param(CATHEDRA_ID, valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, GROUPS)));
    }

    @Test
    void shouldReturnCorrectedGroupsPageWhenDeleteGroupsPage() throws Exception {
        mockMvc.perform(delete(DELETE_GROUP_VIEW_NAME))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, GROUPS)));
    }

    @Test
    void shouldReturnCorrectedGroupsPageWhenEditGroupsPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_GROUP_URL_TEMPLATE)
            .param(NAME, GROUP_1_NAME_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, GROUPS)));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetGroupsPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_ALL_PROPERTY_NAME))
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetGroupsPageByCathedraId() throws Exception {
        mockMvc.perform(get(GET_BY_CATHEDRA_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_CATHEDRA_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_CATHEDRA_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetGroupsPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetNewGroupPage() throws Exception {
        mockMvc.perform(get(GET_NEW_GROUP_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_NEW_GROUP_PROPERTY_NAME))
            .andExpect(model().attributeExists(GET_NEW_GROUP_CATHEDRAS_PROPERTY_NAME))
            .andExpect(view().name(GET_NEW_GROUP_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenCreateNewGroupPage() throws Exception {
        mockMvc.perform(post(POST_NEW_GROUP_URL_TEMPLATE)
            .param(NAME, GROUP_1_NAME_VALUE)
            .param(CATHEDRA_ID, valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(GROUP));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditGroupPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_GROUP_URL_TEMPLATE)
            .param(NAME, GROUP_1_NAME_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(GROUP));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetEditGroupPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_GROUP_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_EDIT_GROUP_PROPERTY_NAME))
            .andExpect(view().name(GET_EDIT_GROUP_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedGroupAttributesWhenGetGroups() throws Exception {
        Cathedra physicsCathedra = new Cathedra(ID_1_VALUE, PHYSICS);
        Cathedra medicalsCathedra = new Cathedra(ID_2_VALUE, MEDICALS);

        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_2))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(NAME, is(GROUP_1_NAME_VALUE)),
                    hasProperty(STUDENTS, is(nullValue())),
                    hasProperty(CATHEDRA, is(physicsCathedra))
                ))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(NAME, is(GROUP_2_NAME_VALUE)),
                    hasProperty(STUDENTS, is(nullValue())),
                    hasProperty(CATHEDRA, is(medicalsCathedra))
                ))));
    }

    @Test
    void shouldReturnCorrectedGroupAttributesWhenGetGroupsByCathedraId() throws Exception {
        Cathedra physicsCathedra = new Cathedra(ID_1_VALUE, PHYSICS);

        mockMvc.perform(get(GET_BY_CATHEDRA_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_CATHEDRA_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_CATHEDRA_ID_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(NAME, is(GROUP_1_NAME_VALUE)),
                    hasProperty(STUDENTS, is(nullValue())),
                    hasProperty(CATHEDRA, is(physicsCathedra))
                ))));
    }

    @Test
    void shouldReturnCorrectedGroupAttributesWhenGetGroupById() throws Exception {
        List<Student> students = singletonList(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, students, new Cathedra(ID_1_VALUE, PHYSICS));

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(group)));
    }
}