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

import javax.sql.DataSource;

import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebTestConfig.class})
@WebAppConfiguration
class CathedraControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/cathedras/";
    private static final String GET_ALL_VIEW_NAME = "/cathedras/cathedras";
    private static final String GET_ALL_PROPERTY_NAME = "cathedras";

    private static final String GET_BY_ID_URL_TEMPLATE = "/cathedras/1";
    private static final String GET_BY_ID_VIEW_NAME = "/cathedras/cathedra-info";
    private static final String GET_BY_ID_PROPERTY_NAME = "cathedra";

    private static final String GET_NEW_CATHEDRA_URL_TEMPLATE = "/cathedras/new";
    private static final String GET_NEW_CATHEDRA_PROPERTY_NAME = "cathedra";
    private static final String GET_NEW_CATHEDRA_VIEW_NAME = "/cathedras/new-cathedra";
    private static final String POST_NEW_CATHEDRA_URL_TEMPLATE = "/cathedras/";

    private static final String GET_EDIT_CATHEDRA_URL_TEMPLATE = "/cathedras/1/edit";
    private static final String GET_EDIT_CATHEDRA_PROPERTY_NAME = "cathedra";
    private static final String GET_EDIT_CATHEDRA_VIEW_NAME = "/cathedras/cathedra-update";
    private static final String POST_EDIT_CATHEDRA_URL_TEMPLATE = "/cathedras/1";

    private static final String DELETE_CATHEDRA_VIEW_NAME = "/cathedras/1";

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
    void shouldReturnCorrectedCathedraPageWhenGetCathedraPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedCathedraPageWhenGetCathedraPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedCathedrasPageWhenGetNewCathedraPage() throws Exception {
        mockMvc.perform(get(GET_NEW_CATHEDRA_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_CATHEDRA_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedCathedrasPageWhenGetEditCathedraPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_CATHEDRA_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_CATHEDRA_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedCathedrasPageWhenCreateNewCathedraPage() throws Exception {
        mockMvc.perform(post(POST_NEW_CATHEDRA_URL_TEMPLATE)
            .param(NAME, CATHEDRA_1_NAME_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, CATHEDRAS)));
    }

    @Test
    void shouldReturnCorrectedCathedrasPageWhenDeleteCathedrasPage() throws Exception {
        mockMvc.perform(delete(DELETE_CATHEDRA_VIEW_NAME))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, CATHEDRAS)));
    }

    @Test
    void shouldReturnCorrectedCathedrasPageWhenEditCathedraPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_CATHEDRA_URL_TEMPLATE)
            .param(NAME, CATHEDRA_1_NAME_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, CATHEDRAS)));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetCathedrasPage() throws Exception {
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_ALL_PROPERTY_NAME))
            .andExpect(view().name(GET_ALL_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetCathedraPageById() throws Exception {
        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_BY_ID_PROPERTY_NAME))
            .andExpect(view().name(GET_BY_ID_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetNewCathedraPage() throws Exception {
        mockMvc.perform(get(GET_NEW_CATHEDRA_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_NEW_CATHEDRA_PROPERTY_NAME))
            .andExpect(view().name(GET_NEW_CATHEDRA_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenCreateNewCathedraPage() throws Exception {
        mockMvc.perform(post(POST_NEW_CATHEDRA_URL_TEMPLATE)
            .param(NAME, CATHEDRA_1_NAME_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(CATHEDRA));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditCathedraPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_CATHEDRA_URL_TEMPLATE)
            .param(NAME, CATHEDRA_1_NAME_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists(CATHEDRA));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetEditCathedraPage() throws Exception {
        mockMvc.perform(get(GET_EDIT_CATHEDRA_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(GET_EDIT_CATHEDRA_PROPERTY_NAME))
            .andExpect(view().name(GET_EDIT_CATHEDRA_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedCathedraAttributesWhenGetCathedrass() throws Exception {

        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_2))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(NAME, is(PHYSICS))
                ))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(NAME, is(MEDICALS))
                ))));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetCathedraById() throws Exception {
        Cathedra cathedra = new Cathedra(ID_1_VALUE, PHYSICS);

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(cathedra)));
    }
}