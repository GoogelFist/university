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
import ua.com.foxminded.university.entities.Teacher;

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
class TeacherDAOImplTest {
    @Autowired
    private TeacherDAO teacherDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateTeacher() throws DaoException {
        Teacher expectedTeacher = new Teacher(ID_3_VALUE, TEACHER_3_FIRST_NAME_VALUE, TEACHER_3_LAST_NAME_VALUE, TEACHER_3_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE));
        teacherDAO.create(expectedTeacher);
        Teacher actualTeacher = teacherDAO.getById(ID_3_VALUE);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void shouldGetTeacherByID() throws DaoException {
        Teacher expectedTeacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE));
        Teacher actualTeacher = teacherDAO.getById(ID_1_VALUE);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionExceptionWhenCantGetTeacherById() {
        Exception exception = assertThrows(DaoException.class, () -> teacherDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, TEACHER, ID_5_VALUE);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllTeachers() throws DaoException {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE)));
        expectedTeachers.add(new Teacher(ID_2_VALUE, TEACHER_2_FIRST_NAME_VALUE, TEACHER_2_LAST_NAME_VALUE, TEACHER_2_PHONE_VALUE, QUALIFICATION_2_VALUE, new Cathedra(ID_2_VALUE)));
        List<Teacher> actualTeachers = teacherDAO.getAll();

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldUpdateTeacher() throws DaoException {
        Teacher expectedTeacher = new Teacher(ID_1_VALUE, TEACHER_2_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE));
        teacherDAO.update(ID_1_VALUE, expectedTeacher);
        Teacher actualTeacher = teacherDAO.getById(ID_1_VALUE);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void delete() throws DaoException {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE)));
        teacherDAO.delete(ID_2_VALUE);
        List<Teacher> actualTeachers = teacherDAO.getAll();

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldGetTeachersByCathedraId() throws DaoException {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE)));

        List<Teacher> actualTeachers = teacherDAO.getByCathedraId(ID_1_VALUE);

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldAssignTeacherToCathedra() throws DaoException {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE)));
        expectedTeachers.add(new Teacher(ID_3_VALUE, TEACHER_3_FIRST_NAME_VALUE, TEACHER_3_LAST_NAME_VALUE, TEACHER_3_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE)));

        teacherDAO.create(new Teacher(ID_3_VALUE, TEACHER_3_FIRST_NAME_VALUE, TEACHER_3_LAST_NAME_VALUE, TEACHER_3_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_2_VALUE)));
        teacherDAO.assignToCathedra(ID_1_VALUE, ID_3_VALUE);
        List<Teacher> actualTeachers = teacherDAO.getByCathedraId(ID_1_VALUE);

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldUpdateAssignmentTeacherToCathedra() throws DaoException {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_2_VALUE)));
        expectedTeachers.add(new Teacher(ID_2_VALUE, TEACHER_2_FIRST_NAME_VALUE, TEACHER_2_LAST_NAME_VALUE, TEACHER_2_PHONE_VALUE, QUALIFICATION_2_VALUE, new Cathedra(ID_2_VALUE)));

        teacherDAO.updateAssignment(ID_2_VALUE, ID_1_VALUE);
        List<Teacher> actualTeachers = teacherDAO.getByCathedraId(ID_2_VALUE);

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldDeleteAssignmentTeacherToCathedra() throws DaoException {
        List<Teacher> expectedTeachers = emptyList();
        teacherDAO.deleteAssignment(ID_1_VALUE);
        teacherDAO.deleteAssignment(ID_2_VALUE);
        List<Teacher> actualTeachers = teacherDAO.getByCathedraId(1);

        assertEquals(expectedTeachers, actualTeachers);
    }
}