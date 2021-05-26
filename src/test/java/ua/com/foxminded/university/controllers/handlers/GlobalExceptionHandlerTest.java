package ua.com.foxminded.university.controllers.handlers;

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
import ua.com.foxminded.university.controllers.WebTestConfig;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebTestConfig.class})
@WebAppConfiguration
class GlobalExceptionHandlerTest {
    private static final String ERROR_URL_TEMPLATE = "/students/101";
    private static final String ERROR_ATTRIBUTE = "exception";
    private static final String ERROR_VIEW_NAME = "error";

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
    void shouldReturnCorrectedErrorPageWhenGetIncorrectStudentsPage() throws Exception {
        mockMvc.perform(get(ERROR_URL_TEMPLATE))
            .andExpect(status().is2xxSuccessful())
            .andExpect(view().name(ERROR_VIEW_NAME));
    }

    @Test
    void shouldCheckForAttributeExistenceWhenGetErrorPage() throws Exception {
        mockMvc.perform(get(ERROR_URL_TEMPLATE))
            .andExpect(status().is2xxSuccessful())
            .andExpect(model().attributeExists(ERROR_ATTRIBUTE))
            .andExpect(view().name(ERROR_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedExceptionAttributesWhenGetIncorrectStudentPage() throws Exception {
        mockMvc.perform(get(ERROR_URL_TEMPLATE))
            .andExpect(status().is2xxSuccessful())
            .andExpect(model().attribute(ERROR_ATTRIBUTE, hasProperty(MESSAGE_PROPERTY_NAME, is(MESSAGE_PROPERTY_VALUE))));
    }
}