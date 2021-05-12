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

@SpringJUnitConfig(DaoTestConfig.class)
class CathedraDAOImplTest {

    private static final String CATHEDRA = "cathedra";
    @Autowired
    private CathedraDAO cathedraDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/testData.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateCathedras() throws DaoException {
        Cathedra expectedCathedra = new Cathedra(3, "maths");
        cathedraDAO.create(expectedCathedra);
        Cathedra actualCathedra = cathedraDAO.getById(3);

        assertEquals(expectedCathedra, actualCathedra);
    }

    @Test
    void shouldGetCathedraByID() throws DaoException {
        Cathedra expectedCathedra = new Cathedra(1, "physics");
        Cathedra actualCathedra = cathedraDAO.getById(1);

        assertEquals(expectedCathedra, actualCathedra);
    }

    @Test
    void shouldThrowDaoExceptionExceptionWhenCantGetCathedraById() {
        int id = 5;
        Exception exception = assertThrows(DaoException.class, () -> cathedraDAO.getById(id));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, CATHEDRA, id);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllCathedras() throws DaoException {
        List<Cathedra> expectedCathedras = new ArrayList<>();
        expectedCathedras.add(new Cathedra(1, "physics"));
        expectedCathedras.add(new Cathedra(2, "medicals"));

        List<Cathedra> actualCAthedras = cathedraDAO.getAll();

        assertEquals(expectedCathedras, actualCAthedras);
    }

    @Test
    void shouldUpdateCathedra() throws DaoException {
        Cathedra expectedCathedra = new Cathedra(1, "maths");
        cathedraDAO.update(1, expectedCathedra);
        Cathedra actualCathedra = cathedraDAO.getById(1);

        assertEquals(expectedCathedra, actualCathedra);
    }

    @Test
    void shouldDeleteCathedra() throws DaoException {
        List<Cathedra> expectedCathedras = new ArrayList<>();
        expectedCathedras.add(new Cathedra(1, "physics"));
        cathedraDAO.delete(2);
        List<Cathedra> actualCathedras = cathedraDAO.getAll();

        assertEquals(expectedCathedras, actualCathedras);
    }
}