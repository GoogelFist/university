package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@Transactional
class StudentDAOImpTest {
    @Autowired
    private StudentDAO studentDAO;

    @Test
    void shouldCreateStudent() {
        Student expectedStudent = new Student(ID_3_VALUE, STUDENT_3_FIRST_NAME_VALUE, STUDENT_3_LAST_NAME_VALUE, STUDENT_3_PHONE_VALUE);

        studentDAO.create(expectedStudent);
        Student actualStudent = studentDAO.getById(ID_3_VALUE);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldGetStudentByID() {
        Student expectedStudent = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE);

        Student actualStudent = studentDAO.getById(ID_1_VALUE);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldGetAllStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE));
        expectedStudents.add(new Student(ID_2_VALUE, STUDENT_2_FIRST_NAME_VALUE, STUDENT_2_LAST_NAME_VALUE, STUDENT_2_PHONE_VALUE));

        List<Student> actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldUpdateStudent() {
        Student expectedStudent = new Student(ID_1_VALUE, STUDENT_3_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE);

        studentDAO.update(expectedStudent);

        Student actualStudent = studentDAO.getById(ID_1_VALUE);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldDeleteStudent() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE));

        studentDAO.delete(ID_2_VALUE);

        List<Student> actualStudents = studentDAO.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void shouldGetStudentsByGroupId() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE));

        List<Student> actualStudent = studentDAO.getByGroupId(ID_1_VALUE);

        assertEquals(expectedStudents, actualStudent);
    }
}