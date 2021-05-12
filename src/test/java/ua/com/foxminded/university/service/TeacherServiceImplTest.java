package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.TeacherDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class TeacherServiceImplTest {
    @Mock
    private TeacherDAO mockTeacherDAO;

    @Mock
    private CathedraDAO mockCathedraDAO;

    private TeacherService teacherService;
    private Teacher teacher;
    private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherServiceImpl(mockTeacherDAO, mockCathedraDAO);
        teacher = new Teacher(1, "David", "Denver", "12345", "1");
        teachers = singletonList(teacher);
    }

    @Test
    void shouldCallCreateTeacher() throws DaoException {
        teacherService.create(teacher);

        verify(mockTeacherDAO, times(1)).create(teacher);
    }

    @Test
    void shouldCallGetTeacherByID() throws DaoException {
        when(mockTeacherDAO.getById(1)).thenReturn(teacher);
        Teacher actualTeacher = teacherService.getById(1);

        verify(mockTeacherDAO, times(1)).getById(1);
        assertEquals(teacher, actualTeacher);
    }

    @Test
    void shouldCallGetAllTeachers() throws DaoException {
        when(mockTeacherDAO.getAll()).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getAll();

        verify(mockTeacherDAO, times(1)).getAll();
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallUpdateTeacher() throws DaoException {
        teacherService.update(1, teacher);

        verify(mockTeacherDAO, times(1)).update(1, teacher);
    }

    @Test
    void shouldCallDeleteTeacher() throws DaoException {
        teacherService.delete(1);

        verify(mockTeacherDAO, times(1)).delete(1);
    }

    @Test
    void shouldCallGetTeachersByCathedraID() throws DaoException {
        when(mockTeacherDAO.getByCathedraId(1)).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getByCathedraId(1);

        verify(mockTeacherDAO, times(1)).getByCathedraId(1);
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallAssignTeacherToCathedra() throws DaoException {
        teacherService.assignToCathedra(1, 1);

        verify(mockTeacherDAO, times(1)).assignToCathedra(1, 1);
    }

    @Test
    void shouldCallUpdateAssignmentTeacherToCathedra() throws DaoException {
        teacherService.updateAssignment(1, 1);

        verify(mockTeacherDAO, times(1)).getById(anyInt());
        verify(mockCathedraDAO, times(1)).getById(anyInt());
        verify(mockTeacherDAO, times(1)).updateAssignment(1, 1);
    }

    @Test
    void shouldCallDeleteAssignmentTeacherToCathedra() throws DaoException {
        teacherService.deleteAssignment(1);

        verify(mockTeacherDAO, times(1)).deleteAssignment(1);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateTeacher() throws DaoException {
        doThrow(new ServiceException("Unable to create teacher")).when(mockTeacherDAO).create(teacher);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.create(teacher));
        String actual = exception.getMessage();
        String expected = "Unable to create teacher";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByTeacherId() throws DaoException {
        doThrow(new ServiceException("Unable to get teacher with ID 5")).when(mockTeacherDAO).getById(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.getById(5));
        String actual = exception.getMessage();
        String expected = "Unable to get teacher with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllTeachers() throws DaoException {
        doThrow(new ServiceException("Unable to get all teachers")).when(mockTeacherDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.getAll());
        String actual = exception.getMessage();
        String expected = "Unable to get all teachers";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateTeacher() throws DaoException {
        doThrow(new ServiceException("Unable to update teacher with ID 5")).when(mockTeacherDAO).update(5, teacher);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.update(5, teacher));
        String actual = exception.getMessage();
        String expected = "Unable to update teacher with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteTeacher() throws DaoException {
        doThrow(new ServiceException("Unable to delete teacher with ID 5")).when(mockTeacherDAO).delete(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.delete(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete teacher with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetTeacherByCathedraId() throws DaoException {
        doThrow(new ServiceException("Unable to get teacher by cathedraId 5")).when(mockTeacherDAO).getByCathedraId(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.getByCathedraId(5));
        String actual = exception.getMessage();
        String expected = "Unable to get teacher by cathedraId 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantAssignToCathedra() throws DaoException {
        doThrow(new ServiceException("Unable assign teacher with id 5 to cathedra with id 3")).when(mockTeacherDAO).assignToCathedra(3, 5);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.assignToCathedra(3, 5));
        String actual = exception.getMessage();
        String expected = "Unable assign teacher with id 5 to cathedra with id 3";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateAssignment() throws DaoException {
        doThrow(new ServiceException("Unable to update assignment teacher id 5 to cathedra with id 3")).when(mockTeacherDAO).updateAssignment(3, 5);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.updateAssignment(3, 5));
        String actual = exception.getMessage();
        String expected = "Unable to update assignment teacher id 5 to cathedra with id 3";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteAssignment() throws DaoException {
        doThrow(new ServiceException("Unable to delete assignment teacher id 5 from cathedra")).when(mockTeacherDAO).deleteAssignment(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.deleteAssignment(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete assignment teacher id 5 from cathedra";

        assertEquals(expected, actual);
    }
}