package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Group;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringJUnitConfig(DaoTestConfig.class)
@Transactional
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
    void shouldCreateGroup() {
        Group expectedGroup = new Group(ID_3_VALUE, GROUP_3_NAME_VALUE);
        groupDAO.create(expectedGroup);
        Group actualGroup = groupDAO.getById(ID_3_VALUE);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldGetGroupByID() {
        Group expectedGroup = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE);
        Group actualGroup = groupDAO.getById(ID_1_VALUE);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldGetAllGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE));
        expectedGroups.add(new Group(ID_2_VALUE, GROUP_2_NAME_VALUE));

        List<Group> actualGroup = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroup);
    }

    @Test
    void shouldUpdateGroup() {
        Group expectedGroup = new Group(ID_1_VALUE, GROUP_3_NAME_VALUE);
        groupDAO.update(expectedGroup);
        Group actualGroup = groupDAO.getById(ID_1_VALUE);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldDeleteGroup() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE));
        groupDAO.delete(ID_2_VALUE);
        List<Group> actualGroups = groupDAO.getAll();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldGetGroupsByCathedraId() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE));
        List<Group> actualGroups = groupDAO.getByCathedraId(ID_1_VALUE);

        assertEquals(expectedGroups, actualGroups);
    }
}