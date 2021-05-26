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

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.ENTITY_NOT_FOUND;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringJUnitConfig(DaoTestConfig.class)
class CathedraDAOImplTest {
    @Autowired
    private CathedraDAO cathedraDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateCathedras() throws DaoException {
        Cathedra expectedCathedra = new Cathedra(ID_3_VALUE, CATHEDRA_3_NAME_VALUE);
        cathedraDAO.create(expectedCathedra);
        Cathedra actualCathedra = cathedraDAO.getById(ID_3_VALUE);

        assertEquals(expectedCathedra, actualCathedra);
    }

    @Test
    void shouldGetCathedraByID() throws DaoException {
        Cathedra expectedCathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        Cathedra actualCathedra = cathedraDAO.getById(ID_1_VALUE);

        assertEquals(expectedCathedra, actualCathedra);
    }

    @Test
    void shouldThrowDaoExceptionExceptionWhenCantGetCathedraById() {
        Exception exception = assertThrows(DaoException.class, () -> cathedraDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, CATHEDRA, ID_5_VALUE);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllCathedras() throws DaoException {
        List<Cathedra> expectedCathedras = new ArrayList<>();
        expectedCathedras.add(new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE));
        expectedCathedras.add(new Cathedra(ID_2_VALUE, CATHEDRA_2_NAME_VALUE));

        List<Cathedra> actualCAthedras = cathedraDAO.getAll();

        assertEquals(expectedCathedras, actualCAthedras);
    }

    @Test
    void shouldUpdateCathedra() throws DaoException {
        Cathedra expectedCathedra = new Cathedra(ID_1_VALUE, CATHEDRA_3_NAME_VALUE);
        cathedraDAO.update(ID_1_VALUE, expectedCathedra);
        Cathedra actualCathedra = cathedraDAO.getById(ID_1_VALUE);

        assertEquals(expectedCathedra, actualCathedra);
    }

    @Test
    void shouldDeleteCathedra() throws DaoException {
        List<Cathedra> expectedCathedras = new ArrayList<>();
        expectedCathedras.add(new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE));
        cathedraDAO.delete(ID_2_VALUE);
        List<Cathedra> actualCathedras = cathedraDAO.getAll();

        assertEquals(expectedCathedras, actualCathedras);
    }
}