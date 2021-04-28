package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.entities.Student;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringJUnitConfig(DaoTestConfig.class)
class StudentDAOImpTest {

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/testData.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateStudent() {
        Student expectedStudent = new Student(3, "John", "Manson", "777");
        studentDAO.create(expectedStudent);
        Student actualStudent = studentDAO.getById(3);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldGetStudentByID() {
        Student expectedStudent = new Student(1, "James", "Gosling", "12345");
        Student actualStudent = studentDAO.getById(1);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldGetAllStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "James", "Gosling", "12345"));
        expectedStudents.add(new Student(2, "Mikhail", "Denver", "54321"));
        List<Student> actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldUpdateStudent() {
        Student expectedStudent = new Student(1, "Donald", "Gosling", "12345");
        studentDAO.update(1, expectedStudent);
        Student actualStudent = studentDAO.getById(1);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldDeleteStudent() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "James", "Gosling", "12345"));
        studentDAO.delete(2);
        List<Student> actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldGetStudentsByGroupId() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "James", "Gosling", "12345"));
        expectedStudents.add(new Student(2, "Mikhail", "Denver", "54321"));
        List<Student> actualStudent = studentDAO.getByGroupId(1);

        assertEquals(expectedStudents, actualStudent);
    }

    @Test
    void shouldAssignStudentsToGroup() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "James", "Gosling", "12345"));
        expectedStudents.add(new Student(2, "Mikhail", "Denver", "54321"));
        expectedStudents.add(new Student(3, "John", "Manson", "777"));

        studentDAO.create(new Student(3, "John", "Manson", "777"));
        studentDAO.assignToGroup(1, 3);
        List<Student> actualStudents = studentDAO.getByGroupId(1);

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldUpdateAssignmentStudentToGroup() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "James", "Gosling", "12345"));

        studentDAO.updateAssignment(2, 1);
        List<Student> actualStudents = studentDAO.getByGroupId(2);

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldDeleteAssignmentStudentToGroup() {
        List<Student> expectedStudents = emptyList();
        studentDAO.deleteAssignment(1);
        studentDAO.deleteAssignment(2);
        List<Student> actualStudents = studentDAO.getByGroupId(1);

        assertEquals(expectedStudents, actualStudents);
    }
}