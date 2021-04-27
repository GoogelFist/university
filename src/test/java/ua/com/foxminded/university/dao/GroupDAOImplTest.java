package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.entities.Group;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(DaoTestConfig.class)
class GroupDAOImplTest {

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
    void shouldCreateGroup() {
        Group expectedGroup = new Group(3, "CC-10");
        groupDAO.create(expectedGroup);
        Group actualGroup = groupDAO.getByID(3);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldGetGroupByID() {
        Group expectedGroup = new Group(1, "AC-10");
        Group actualGroup = groupDAO.getByID(1);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldGetAllGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AC-10"));
        expectedGroups.add(new Group(2, "BC-20"));

        List<Group> actualGroup = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroup);
    }

    @Test
    void shouldUpdateGroup() {
        Group expectedGroup = new Group(1, "ABC-123");
        groupDAO.update(1, expectedGroup);
        Group actualGroup = groupDAO.getByID(1);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldDeleteGroup() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AC-10"));
        groupDAO.delete(2);
        List<Group> actualGroups = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroups);
    }
}