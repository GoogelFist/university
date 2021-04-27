package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.entities.Teacher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(DaoTestConfig.class)
class TeacherDAOImplTest {

    @Autowired
    private TeacherDAO teacherDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/testData.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateTeacher() {
        Teacher expectedTeacher = new Teacher(3, "Petr", "Pavlov", "123123", "1");
        teacherDAO.create(expectedTeacher);
        Teacher actualTeacher = teacherDAO.getByID(3);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void shouldGetTeacherByID() {
        Teacher expectedTeacher = new Teacher(1, "Jonathan", "Bride", "612345", "1");
        Teacher actualTeacher = teacherDAO.getByID(1);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void shouldGetAllTeachers() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(1, "Jonathan", "Bride", "612345", "1"));
        expectedTeachers.add(new Teacher(2, "Bill", "Noise", "64321", "2"));
        List<Teacher> actualTeachers = teacherDAO.getAll();

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldUpdateTeacher() {
        Teacher expectedTeacher = new Teacher(1, "Barney", "White", "35434", "5");
        teacherDAO.update(1, expectedTeacher);
        Teacher actualTeacher = teacherDAO.getByID(1);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void delete() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(1, "Jonathan", "Bride", "612345", "1"));
        teacherDAO.delete(2);
        List<Teacher> actualTeachers = teacherDAO.getAll();

        assertEquals(expectedTeachers, actualTeachers);
    }
}