package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.dto.StudentDto;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.properties")
class StudentControllerTest {
    private static final String GET_ALL_URL_TEMPLATE = "/students/";
    private static final String GET_ALL_VIEW_NAME = "/students/students";
    private static final String GET_ALL_PROPERTY_NAME = "students";

    private static final String GET_BY_GROUP_ID_URL_TEMPLATE = "/students/by-group/1";
    private static final String GET_BY_GROUP_ID_VIEW_NAME = "/students/students-by-group-id";
    private static final String GET_BY_GROUP_ID_PROPERTY_NAME = "students";

    private static final String GET_BY_ID_URL_TEMPLATE = "/students/1";
    private static final String GET_BY_ID_VIEW_NAME = "/students/student-info";
    private static final String GET_BY_ID_PROPERTY_NAME = "studentDto";

    private static final String GET_NEW_STUDENT_URL_TEMPLATE = "/students/new";
    private static final String GET_NEW_STUDENT_PROPERTY_NAME = "studentDto";
    private static final String GET_NEW_STUDENT_GROUPS_PROPERTY_NAME = "groups";
    private static final String GET_NEW_STUDENT_VIEW_NAME = "/students/student-new";
    private static final String POST_NEW_STUDENT_URL_TEMPLATE = "/students/";

    private static final String GET_EDIT_STUDENT_URL_TEMPLATE = "/students/1/edit";
    private static final String GET_EDIT_STUDENT_PROPERTY_NAME = "studentDto";
    private static final String GET_EDIT_STUDENT_VIEW_NAME = "/students/student-update";
    private static final String POST_EDIT_STUDENT_URL_TEMPLATE = "/students/1";

    private static final String DELETE_STUDENT_VIEW_NAME = "/students/1";


    @Autowired
    public MockMvc mockMvc;

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
            .param(GROUP_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, STUDENTS)));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenCreateNewStudentsPageWithInvalidateParameters() throws Exception {
        mockMvc.perform(post(POST_NEW_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, EMPTY_STRING)
            .param(LAST_NAME, EMPTY_STRING)
            .param(PHONE, EMPTY_STRING)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_NEW_STUDENT_VIEW_NAME));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenEditStudentsPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, STUDENT_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, STUDENT_1_LAST_NAME_VALUE)
            .param(PHONE, STUDENT_1_PHONE_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(format(REDIRECT, STUDENTS)));
    }

    @Test
    void shouldReturnCorrectedStudentsPageWhenEditStudentsPageWithInvalidateParameters() throws Exception {
        mockMvc.perform(patch(POST_EDIT_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, EMPTY_STRING)
            .param(LAST_NAME, EMPTY_STRING)
            .param(PHONE, EMPTY_STRING)
            .param(GROUP_ID, valueOf(ID_1_VALUE)))
            .andExpect(status().isOk())
            .andExpect(view().name(GET_EDIT_STUDENT_VIEW_NAME));
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
            .param(GROUP_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditStudentPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_STUDENT_URL_TEMPLATE)
            .param(FIRST_NAME, STUDENT_1_FIRST_NAME_VALUE)
            .param(LAST_NAME, STUDENT_1_LAST_NAME_VALUE)
            .param(PHONE, STUDENT_1_PHONE_VALUE)
            .param(GROUP_ID, String.valueOf(ID_1_VALUE)))
            .andExpect(status().is3xxRedirection());
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
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_2))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(FIRST_NAME, is(STUDENT_1_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(STUDENT_1_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(STUDENT_1_PHONE_VALUE))
                ))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(FIRST_NAME, is(STUDENT_2_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(STUDENT_2_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(STUDENT_2_PHONE_VALUE))
                ))));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetStudentsByGroupId() throws Exception {
        mockMvc.perform(get(GET_BY_GROUP_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_GROUP_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_GROUP_ID_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(FIRST_NAME, is(STUDENT_1_FIRST_NAME_VALUE)),
                    hasProperty(LAST_NAME, is(STUDENT_1_LAST_NAME_VALUE)),
                    hasProperty(PHONE, is(STUDENT_1_PHONE_VALUE))
                ))));
    }

    @Test
    void shouldReturnCorrectedStudentAttributesWhenGetStudentById() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(ID_1_VALUE);
        studentDto.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDto.setLastName(STUDENT_1_LAST_NAME_VALUE);
        studentDto.setPhone(STUDENT_1_PHONE_VALUE);
        studentDto.setGroupId(ID_1_VALUE);
        studentDto.setGroupName(GROUP_1_NAME_VALUE);

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(studentDto)));
    }
}