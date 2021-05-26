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
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Group;
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

    @Mock
    private GroupDAO mockGroupDAO;

    private StudentService studentService;
    private Student student;
    private List<Student> students;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(mockStudentDAO, mockGroupDAO);
        student = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE));
        students = singletonList(student);
    }

    @Test
    void shouldCallCreateStudent() throws DaoException {
        studentService.create(student);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(student);
    }

    @Test
    void shouldCallGetStudentByID() throws DaoException {
        when(mockStudentDAO.getById(1)).thenReturn(student);
        Student actualStudent = studentService.getById(ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(student, actualStudent);
    }

    @Test
    void shouldCallGetAllStudents() throws DaoException {
        when(mockStudentDAO.getAll()).thenReturn(students);
        List<Student> actualStudents = studentService.getAll();

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallGetAllStudentsPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        students = new ArrayList<>();
        students.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        when(mockStudentDAO.getAll()).thenReturn(students);

        Page<Student> expectedPageStudents = new PageImpl<>(students, pageable, students.size());
        Page<Student> actualPageStudents = studentService.getAll(pageable);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallUpdateStudent() throws DaoException {
        studentService.update(ID_1_VALUE, student);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(ID_1_VALUE, student);
    }

    @Test
    void shouldCallDeleteStudent() throws DaoException {
        studentService.delete(ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetStudentsByGroupId() throws DaoException {
        when(mockStudentDAO.getByGroupId(ID_1_VALUE)).thenReturn(students);
        List<Student> actualStudents = studentService.getByGroupId(ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallGetStudentsByGroupIdPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        students = new ArrayList<>();
        students.add(new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE)));
        when(mockStudentDAO.getByGroupId(ID_1_VALUE)).thenReturn(students);

        Page<Student> expectedPageStudents = new PageImpl<>(students, pageable, students.size());
        Page<Student> actualPageStudents = studentService.getByGroupId(ID_1_VALUE, pageable);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallAssignStudentToGroup() throws DaoException {
        studentService.assignToGroup(ID_1_VALUE, ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).assignToGroup(ID_1_VALUE, ID_1_VALUE);
    }

    @Test
    void shouldCallUpdateAssignmentStudentToGroup() throws DaoException {
        studentService.updateAssignment(ID_1_VALUE, ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).updateAssignment(ID_1_VALUE, ID_1_VALUE);
    }

    @Test
    void shouldCallDeleteAssignmentStudentToGroup() throws DaoException {
        studentService.deleteAssignment(ID_1_VALUE);

        verify(mockStudentDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteAssignment(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateStudent() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_CREATE, STUDENT);
        doThrow(new ServiceException(message)).when(mockStudentDAO).create(student);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.create(student));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByStudentId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, GET, STUDENT, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockStudentDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllStudents() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_GET_ALL, STUDENTS);
        doThrow(new ServiceException(message)).when(mockStudentDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.getAll());
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateStudent() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, UPDATE, STUDENT, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockStudentDAO).update(ID_5_VALUE, student);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.update(ID_5_VALUE, student));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteStudent() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, DELETE, STUDENT, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockStudentDAO).delete(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.delete(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantAssignToGroup() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGN, ASSIGN, STUDENT, ID_5_VALUE, GROUP, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockStudentDAO).assignToGroup(ID_3_VALUE, ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.assignToGroup(ID_3_VALUE, ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateAssignment() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGNMENT, UPDATE, STUDENT, ID_5_VALUE, GROUP, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockStudentDAO).updateAssignment(ID_3_VALUE, ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.updateAssignment(ID_3_VALUE, ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteAssignment() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGNMENT, DELETE, STUDENT, ID_5_VALUE, GROUP, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockStudentDAO).deleteAssignment(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.deleteAssignment(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}