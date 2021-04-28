package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class StudentServiceImplTest {
    @Mock
    private StudentDAO mockStudentDAO;

    private StudentService studentService;
    private Student student;
    private List<Student> students;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(mockStudentDAO);
        student = new Student(1, "James", "White", "12345");
        students = singletonList(student);
    }

    @Test
    void shouldCallCreateStudent() {
        studentService.create(student);

        verify(mockStudentDAO, times(1)).create(student);
    }

    @Test
    void shouldCallGetStudentByID() {
        when(mockStudentDAO.getById(1)).thenReturn(student);
        Student actualStudent = studentService.getById(1);

        verify(mockStudentDAO, times(1)).getById(1);
        assertEquals(student, actualStudent);
    }

    @Test
    void shouldCallGetAllStudents() {
        when(mockStudentDAO.getAll()).thenReturn(students);
        List<Student> actualStudents = studentService.getAll();

        verify(mockStudentDAO, times(1)).getAll();
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallUpdateStudent() {
        studentService.update(1, student);

        verify(mockStudentDAO, times(1)).update(1, student);
    }

    @Test
    void shouldCallDeleteStudent() {
        studentService.delete(1);

        verify(mockStudentDAO, times(1)).delete(1);
    }

    @Test
    void shouldCallGetStudentsByGroupId() {
        when(mockStudentDAO.getByGroupId(1)).thenReturn(students);
        List<Student> actualStudents = studentService.getByGroupId(1);

        verify(mockStudentDAO, times(1)).getByGroupId(1);
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallAssignStudentToGroup() {
        studentService.assignToGroup(1, 1);

        verify(mockStudentDAO, times(1)).assignToGroup(1, 1);
    }

    @Test
    void shouldCallUpdateAssignmentStudentToGroup() {
        studentService.updateAssignment(1, 1);

        verify(mockStudentDAO, times(1)).updateAssignment(1, 1);
    }

    @Test
    void shouldCallDeleteAssignmentStudentToGroup() {
        studentService.deleteAssignment(1);

        verify(mockStudentDAO, times(1)).deleteAssignment(1);
    }
}