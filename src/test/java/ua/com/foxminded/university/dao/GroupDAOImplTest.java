package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Group;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.ENTITY_NOT_FOUND;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringJUnitConfig(DaoTestConfig.class)
class GroupDAOImplTest {
    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateGroup() throws DaoException {
        Group expectedGroup = new Group(ID_3_VALUE, GROUP_3_NAME_VALUE, new Cathedra(ID_1_VALUE));
        groupDAO.create(expectedGroup);
        Group actualGroup = groupDAO.getById(ID_3_VALUE);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldGetGroupByID() throws DaoException {
        Group expectedGroup = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE));
        Group actualGroup = groupDAO.getById(ID_1_VALUE);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionExceptionWhenCantGetGroupById() {
        Exception exception = assertThrows(DaoException.class, () -> groupDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, GROUP, ID_5_VALUE);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllGroups() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE)));
        expectedGroups.add(new Group(ID_2_VALUE, GROUP_2_NAME_VALUE, new Cathedra(ID_2_VALUE)));

        List<Group> actualGroup = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroup);
    }

    @Test
    void shouldUpdateGroup() throws DaoException {
        Group expectedGroup = new Group(ID_1_VALUE, GROUP_3_NAME_VALUE, new Cathedra(ID_1_VALUE));
        groupDAO.update(1, expectedGroup);
        Group actualGroup = groupDAO.getById(ID_1_VALUE);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldDeleteGroup() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE)));
        groupDAO.delete(2);
        List<Group> actualGroups = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldGetGroupsByCathedraId() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE)));
        List<Group> actualGroups = groupDAO.getByCathedraId(ID_1_VALUE);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldAssignGroupToCathedra() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE)));
        expectedGroups.add(new Group(ID_3_VALUE, GROUP_3_NAME_VALUE, new Cathedra(ID_1_VALUE)));

        groupDAO.create(new Group(ID_3_VALUE, GROUP_3_NAME_VALUE, new Cathedra(ID_2_VALUE)));
        groupDAO.assignToCathedra(ID_1_VALUE, ID_3_VALUE);
        List<Group> actualGroups = groupDAO.getByCathedraId(ID_1_VALUE);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldUpdateAssignmentGroupToCathedra() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_2_VALUE)));
        expectedGroups.add(new Group(ID_2_VALUE, GROUP_2_NAME_VALUE, new Cathedra(ID_2_VALUE)));

        groupDAO.updateAssignment(ID_2_VALUE, ID_1_VALUE);
        List<Group> actualGroups = groupDAO.getByCathedraId(ID_2_VALUE);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldDeleteAssignmentGroupToCathedra() throws DaoException {
        List<Group> expectedGroups = emptyList();
        groupDAO.deleteAssignment(ID_1_VALUE);
        groupDAO.deleteAssignment(ID_2_VALUE);
        List<Group> actualGroups = groupDAO.getByCathedraId(ID_1_VALUE);

        assertEquals(expectedGroups, actualGroups);
    }
}