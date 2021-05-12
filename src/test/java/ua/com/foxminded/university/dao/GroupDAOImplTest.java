package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Group;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.ENTITY_NOT_FOUND;

@SpringJUnitConfig(DaoTestConfig.class)
class GroupDAOImplTest {

    private static final String GROUP = "group";
    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/testData.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateGroup() throws DaoException {
        Group expectedGroup = new Group(3, "CC-10");
        groupDAO.create(expectedGroup);
        Group actualGroup = groupDAO.getById(3);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldGetGroupByID() throws DaoException {
        Group expectedGroup = new Group(1, "AC-10");
        Group actualGroup = groupDAO.getById(1);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionExceptionWhenCantGetGroupById() {
        int id = 5;
        Exception exception = assertThrows(DaoException.class, () -> groupDAO.getById(id));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, GROUP, id);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllGroups() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AC-10"));
        expectedGroups.add(new Group(2, "BC-20"));

        List<Group> actualGroup = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroup);
    }

    @Test
    void shouldUpdateGroup() throws DaoException {
        Group expectedGroup = new Group(1, "ABC-123");
        groupDAO.update(1, expectedGroup);
        Group actualGroup = groupDAO.getById(1);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldDeleteGroup() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AC-10"));
        groupDAO.delete(2);
        List<Group> actualGroups = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldGetGroupsByCathedraId() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AC-10"));
        expectedGroups.add(new Group(2, "BC-20"));
        List<Group> actualGroups = groupDAO.getByCathedraId(1);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldAssignGroupToCathedra() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AC-10"));
        expectedGroups.add(new Group(2, "BC-20"));
        expectedGroups.add(new Group(3, "CC-30"));

        groupDAO.create(new Group(3, "CC-30"));
        groupDAO.assignToCathedra(1, 3);
        List<Group> actualGroups = groupDAO.getByCathedraId(1);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldUpdateAssignmentGroupToCathedra() throws DaoException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AC-10"));

        groupDAO.updateAssignment(2, 1);
        List<Group> actualGroups = groupDAO.getByCathedraId(2);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldDeleteAssignmentGroupToCathedra() throws DaoException {
        List<Group> expectedGroups = emptyList();
        groupDAO.deleteAssignment(1);
        groupDAO.deleteAssignment(2);
        List<Group> actualGroups = groupDAO.getByCathedraId(1);

        assertEquals(expectedGroups, actualGroups);
    }
}