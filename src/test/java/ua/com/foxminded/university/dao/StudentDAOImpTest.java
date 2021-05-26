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
import ua.com.foxminded.university.entities.Student;

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
class StudentDAOImpTest {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateStudent() throws DaoException {
        Student expectedStudent = new Student(ID_3_VALUE, STUDENT_3_FIRST_NAME_VALUE, STUDENT_3_LAST_NAME_VALUE, STUDENT_3_PHONE_VALUE, new Group(ID_1_VALUE));

        studentDAO.create(expectedStudent);
        Student actualStudent = studentDAO.getById(ID_3_VALUE);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldGetStudentByID() throws DaoException {
        Student expectedStudent = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE));

        Student actualStudent = studentDAO.getById(ID_1_VALUE);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionExceptionWhenCantGetStudentById() {
        Exception exception = assertThrows(DaoException.class, () -> studentDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, STUDENT, ID_5_VALUE);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllStudents() throws DaoException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        expectedStudents.add(new Student(ID_2_VALUE, STUDENT_2_FIRST_NAME_VALUE, STUDENT_2_LAST_NAME_VALUE, STUDENT_2_PHONE_VALUE, new Group(ID_2_VALUE)));

        List<Student> actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldUpdateStudent() throws DaoException {
        Student expectedStudent = new Student(ID_1_VALUE, STUDENT_3_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE));

        studentDAO.update(ID_1_VALUE, expectedStudent);
        Student actualStudent = studentDAO.getById(ID_1_VALUE);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldDeleteStudent() throws DaoException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));

        studentDAO.delete(ID_2_VALUE);
        List<Student> actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldGetStudentsByGroupId() throws DaoException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));

        List<Student> actualStudent = studentDAO.getByGroupId(1);

        assertEquals(expectedStudents, actualStudent);
    }

    @Test
    void shouldAssignStudentsToGroup() throws DaoException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        expectedStudents.add(new Student(ID_3_VALUE, STUDENT_3_FIRST_NAME_VALUE, STUDENT_2_LAST_NAME_VALUE, STUDENT_3_PHONE_VALUE, new Group(ID_1_VALUE)));

        studentDAO.create(new Student(ID_3_VALUE, STUDENT_3_FIRST_NAME_VALUE, STUDENT_2_LAST_NAME_VALUE, STUDENT_3_PHONE_VALUE, new Group(ID_2_VALUE)));
        studentDAO.assignToGroup(ID_1_VALUE, ID_3_VALUE);
        List<Student> actualStudents = studentDAO.getByGroupId(ID_1_VALUE);

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldUpdateAssignmentStudentToGroup() throws DaoException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_2_VALUE)));
        expectedStudents.add(new Student(ID_2_VALUE, STUDENT_2_FIRST_NAME_VALUE, STUDENT_2_LAST_NAME_VALUE, STUDENT_2_PHONE_VALUE, new Group(ID_2_VALUE)));

        studentDAO.updateAssignment(ID_2_VALUE, ID_1_VALUE);
        List<Student> actualStudents = studentDAO.getByGroupId(ID_2_VALUE);

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldDeleteAssignmentStudentToGroup() throws DaoException {
        List<Student> expectedStudents = emptyList();

        studentDAO.deleteAssignment(ID_1_VALUE);
        studentDAO.deleteAssignment(ID_2_VALUE);
        List<Student> actualStudents = studentDAO.getByGroupId(ID_1_VALUE);

        assertEquals(expectedStudents, actualStudents);
    }
}