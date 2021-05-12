package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        student = new Student(1, "James", "White", "12345");
        students = singletonList(student);
    }

    @Test
    void shouldCallCreateStudent() throws DaoException {
        studentService.create(student);

        verify(mockStudentDAO, times(1)).create(student);
    }

    @Test
    void shouldCallGetStudentByID() throws DaoException {
        when(mockStudentDAO.getById(1)).thenReturn(student);
        Student actualStudent = studentService.getById(1);

        verify(mockStudentDAO, times(1)).getById(1);
        assertEquals(student, actualStudent);
    }

    @Test
    void shouldCallGetAllStudents() throws DaoException {
        when(mockStudentDAO.getAll()).thenReturn(students);
        List<Student> actualStudents = studentService.getAll();

        verify(mockStudentDAO, times(1)).getAll();
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallUpdateStudent() throws DaoException {
        studentService.update(1, student);

        verify(mockStudentDAO, times(1)).update(1, student);
    }

    @Test
    void shouldCallDeleteStudent() throws DaoException {
        studentService.delete(1);

        verify(mockStudentDAO, times(1)).delete(1);
    }

    @Test
    void shouldCallGetStudentsByGroupId() throws DaoException {
        when(mockStudentDAO.getByGroupId(1)).thenReturn(students);
        List<Student> actualStudents = studentService.getByGroupId(1);

        verify(mockStudentDAO, times(1)).getByGroupId(1);
        assertEquals(students, actualStudents);
    }

    @Test
    void shouldCallAssignStudentToGroup() throws DaoException {
        studentService.assignToGroup(1, 1);

        verify(mockStudentDAO, times(1)).assignToGroup(1, 1);
    }

    @Test
    void shouldCallUpdateAssignmentStudentToGroup() throws DaoException {
        studentService.updateAssignment(1, 1);

        verify(mockGroupDAO, times(1)).getById(anyInt());
        verify(mockStudentDAO, times(1)).getById(anyInt());
        verify(mockStudentDAO, times(1)).updateAssignment(1, 1);
    }

    @Test
    void shouldCallDeleteAssignmentStudentToGroup() throws DaoException {
        studentService.deleteAssignment(1);

        verify(mockStudentDAO, times(1)).deleteAssignment(1);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateStudent() throws DaoException {
        doThrow(new ServiceException("Unable to create student")).when(mockStudentDAO).create(student);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.create(student));
        String actual = exception.getMessage();
        String expected = "Unable to create student";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByStudentId() throws DaoException {
        doThrow(new ServiceException("Unable to get student with ID 5")).when(mockStudentDAO).getById(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.getById(5));
        String actual = exception.getMessage();
        String expected = "Unable to get student with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllStudents() throws DaoException {
        doThrow(new ServiceException("Unable to get all students")).when(mockStudentDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.getAll());
        String actual = exception.getMessage();
        String expected = "Unable to get all students";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateStudent() throws DaoException {
        doThrow(new ServiceException("Unable to update student with ID 5")).when(mockStudentDAO).update(5, student);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.update(5, student));
        String actual = exception.getMessage();
        String expected = "Unable to update student with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteStudent() throws DaoException {
        doThrow(new ServiceException("Unable to delete student with ID 5")).when(mockStudentDAO).delete(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.delete(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete student with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantAssignToGroup() throws DaoException {
        doThrow(new ServiceException("Unable assign student with id 5 to group with id 3")).when(mockStudentDAO).assignToGroup(3, 5);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.assignToGroup(3, 5));
        String actual = exception.getMessage();
        String expected = "Unable assign student with id 5 to group with id 3";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateAssignment() throws DaoException {
        doThrow(new ServiceException("Unable to update assignment student id 5 to group with id 3")).when(mockStudentDAO).updateAssignment(3, 5);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.updateAssignment(3, 5));
        String actual = exception.getMessage();
        String expected = "Unable to update assignment student id 5 to group with id 3";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteAssignment() throws DaoException {
        doThrow(new ServiceException("Unable to delete assignment student id 5 from group")).when(mockStudentDAO).deleteAssignment(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockStudentDAO.deleteAssignment(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete assignment student id 5 from group";

        assertEquals(expected, actual);
    }
}