package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

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
        student = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE);
        students = singletonList(student);
    }

    @Test
    void shouldCallCreateStudent() {
        studentService.create(student);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(student);
    }

    @Test
    void shouldCallGetStudentByID() {
        when(mockStudentDAO.getById(ID_1_VALUE)).thenReturn(student);
        Student actualStudent = studentService.getById(ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(student, actualStudent);
    }

    @Test
    void shouldCallGetAllStudents() {
        when(mockStudentDAO.getAll()).thenReturn(students);
        List<Student> actualStudents = studentService.getAll();

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallGetAllStudentsPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        students = new ArrayList<>();
        students.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE));
        when(mockStudentDAO.getAll()).thenReturn(students);

        Page<Student> expectedPageStudents = new PageImpl<>(students, pageable, students.size());
        Page<Student> actualPageStudents = studentService.getAll(pageable);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallUpdateStudent() {
        studentService.update(student);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(student);
    }

    @Test
    void shouldCallDeleteStudent() {
        studentService.delete(ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetStudentsByGroupId() {
        when(mockStudentDAO.getByGroupId(ID_1_VALUE)).thenReturn(students);
        List<Student> actualStudents = studentService.getByGroupId(ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallGetStudentsByGroupIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        students = new ArrayList<>();
        students.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE));
        when(mockStudentDAO.getByGroupId(ID_1_VALUE)).thenReturn(students);

        Page<Student> expectedPageStudents = new PageImpl<>(students, pageable, students.size());
        Page<Student> actualPageStudents = studentService.getByGroupId(ID_1_VALUE, pageable);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByStudentId() {
        String message = format(ENTITY_NOT_FOUND, STUDENT);
        doThrow(new ServiceException(message)).when(mockStudentDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}