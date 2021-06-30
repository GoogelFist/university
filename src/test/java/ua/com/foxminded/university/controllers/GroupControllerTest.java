package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Group;

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


    @Autowired
    public MockMvc mockMvc;

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
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldCheckForAttributeExistenceWhenEditGroupPage() throws Exception {
        mockMvc.perform(patch(POST_EDIT_GROUP_URL_TEMPLATE)
            .param(NAME, GROUP_1_NAME_VALUE))
            .andExpect(status().is3xxRedirection());
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
        mockMvc.perform(get(GET_ALL_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_2))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(NAME, is(GROUP_1_NAME_VALUE))
                ))))
            .andExpect(model().attribute(GET_ALL_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_2_VALUE)),
                    hasProperty(NAME, is(GROUP_2_NAME_VALUE))
                ))));
    }

    @Test
    void shouldReturnCorrectedGroupAttributesWhenGetGroupsByCathedraId() throws Exception {
        mockMvc.perform(get(GET_BY_CATHEDRA_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_CATHEDRA_ID_PROPERTY_NAME, hasProperty(TOTAL_ELEMENTS, equalTo(TOTAL_ELEMENTS_VALUE_1))))
            .andExpect(model().attribute(GET_BY_CATHEDRA_ID_PROPERTY_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(ID_1_VALUE)),
                    hasProperty(NAME, is(GROUP_1_NAME_VALUE))
                ))));
    }

    @Test
    void shouldReturnCorrectedGroupAttributesWhenGetGroupById() throws Exception {
        Group group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE);

        mockMvc.perform(get(GET_BY_ID_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andExpect(model().attribute(GET_BY_ID_PROPERTY_NAME, equalTo(group)));
    }
}