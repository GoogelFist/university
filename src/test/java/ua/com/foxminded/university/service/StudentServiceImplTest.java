package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.repository.StudentRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class StudentServiceImplTest {
    @Mock
    private StudentRepository mockStudentRepository;

    private StudentService studentService;
    private Student student;
    private List<Student> students;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(mockStudentRepository);
        student = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE);
        students = singletonList(student);
    }

    @Test
    void shouldCallCreateStudent() {
        studentService.create(student);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(student);
    }

    @Test
    void shouldCallGetStudentByID() {
        when(mockStudentRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(student));

        Student actualStudent = studentService.getById(ID_1_VALUE);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(student, actualStudent);
    }

    @Test
    void shouldCallGetAllStudents() {
        when(mockStudentRepository.findAll()).thenReturn(students);
        List<Student> actualStudents = studentService.getAll();

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallGetAllStudentsPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        students = new ArrayList<>();
        students.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE));
        Page<Student> expectedPageStudents = new PageImpl<>(students, pageable, students.size());

        when(mockStudentRepository.findAll(pageable)).thenReturn(expectedPageStudents);

        Page<Student> actualPageStudents = studentService.getAll(pageable);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallUpdateStudent() {
        studentService.update(student);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(student);
    }

    @Test
    void shouldCallDeleteStudent() {
        studentService.delete(ID_1_VALUE);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldCallGetStudentsByGroupId() {
        when(mockStudentRepository.findByGroupId(ID_1_VALUE)).thenReturn(students);
        List<Student> actualStudents = studentService.getByGroupId(ID_1_VALUE);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByGroupId(ID_1_VALUE);
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallGetStudentsByGroupIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        students = new ArrayList<>();
        students.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE));
        Page<Student> expectedPageStudents = new PageImpl<>(students, pageable, students.size());

        when(mockStudentRepository.findByGroupId(ID_1_VALUE, pageable)).thenReturn(expectedPageStudents);

        Page<Student> actualPageStudents = studentService.getByGroupId(ID_1_VALUE, pageable);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByGroupId(ID_1_VALUE, pageable);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByStudentId() {
        String message = format(ENTITY_NOT_FOUND, STUDENT);
        doThrow(new ServiceException(message)).when(mockStudentRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}