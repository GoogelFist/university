package ua.com.foxminded.university.controllers.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.MESSAGE_PROPERTY_NAME;
import static ua.com.foxminded.university.utils.Constants.STUDENT;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class GlobalExceptionHandlerTest {
    private static final String ERROR_URL_TEMPLATE = "/students/101";
    private static final String ERROR_ATTRIBUTE = "exception";
    private static final String ERROR_VIEW_NAME = "error";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String STUDENT_ID = "101";


    @Autowired
    public MockMvc mockMvc;

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
            .andExpect(model().attribute(ERROR_ATTRIBUTE, hasProperty(MESSAGE_PROPERTY_NAME, is(format(ERROR_MESSAGE, STUDENT, STUDENT_ID)))));
    }
}