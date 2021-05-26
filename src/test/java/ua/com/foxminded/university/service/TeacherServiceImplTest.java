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
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.TeacherDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Teacher;
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
        teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE));
        teachers = singletonList(teacher);
    }

    @Test
    void shouldCallCreateTeacher() throws DaoException {
        teacherService.create(teacher);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(teacher);
    }

    @Test
    void shouldCallGetTeacherByID() throws DaoException {
        when(mockTeacherDAO.getById(ID_1_VALUE)).thenReturn(teacher);
        Teacher actualTeacher = teacherService.getById(ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(teacher, actualTeacher);
    }

    @Test
    void shouldCallGetAllTeachers() throws DaoException {
        when(mockTeacherDAO.getAll()).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getAll();

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallGetAllTeachersPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        teachers = new ArrayList<>();
        teachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE)));
        when(mockTeacherDAO.getAll()).thenReturn(teachers);

        Page<Teacher> expectedPageStudents = new PageImpl<>(teachers, pageable, teachers.size());
        Page<Teacher> actualPageStudents = teacherService.getAll(pageable);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallUpdateTeacher() throws DaoException {
        teacherService.update(ID_1_VALUE, teacher);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(ID_1_VALUE, teacher);
    }

    @Test
    void shouldCallDeleteTeacher() throws DaoException {
        teacherService.delete(ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetTeachersByCathedraID() throws DaoException {
        when(mockTeacherDAO.getByCathedraId(ID_1_VALUE)).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getByCathedraId(ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallGetTeachersByCathedraIdPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        teachers = new ArrayList<>();
        teachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE)));
        when(mockTeacherDAO.getByCathedraId(ID_1_VALUE)).thenReturn(teachers);

        Page<Teacher> expectedPageStudents = new PageImpl<>(teachers, pageable, teachers.size());
        Page<Teacher> actualPageStudents = teacherService.getByCathedraId(ID_1_VALUE, pageable);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallAssignTeacherToCathedra() throws DaoException {
        teacherService.assignToCathedra(ID_1_VALUE, ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).assignToCathedra(ID_1_VALUE, ID_1_VALUE);
    }

    @Test
    void shouldCallUpdateAssignmentTeacherToCathedra() throws DaoException {
        teacherService.updateAssignment(ID_1_VALUE, ID_1_VALUE);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).updateAssignment(ID_1_VALUE, ID_1_VALUE);
    }

    @Test
    void shouldCallDeleteAssignmentTeacherToCathedra() throws DaoException {
        teacherService.deleteAssignment(ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteAssignment(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateTeacher() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_CREATE, TEACHER);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).create(teacher);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.create(teacher));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByTeacherId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, GET, TEACHER, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllTeachers() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_GET_ALL, TEACHERS);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.getAll());
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateTeacher() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, UPDATE, TEACHER, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).update(ID_5_VALUE, teacher);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.update(ID_5_VALUE, teacher));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteTeacher() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, DELETE, TEACHER, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).delete(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.delete(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetTeacherByCathedraId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ENTITY_ID, GET, TEACHER, CATHEDRA, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).getByCathedraId(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.getByCathedraId(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantAssignToCathedra() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGN, ASSIGN, TEACHER, ID_5_VALUE, CATHEDRA, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).assignToCathedra(ID_3_VALUE, ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.assignToCathedra(ID_3_VALUE, ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateAssignment() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGNMENT, UPDATE, TEACHER, ID_5_VALUE, CATHEDRA, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).updateAssignment(ID_3_VALUE, ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.updateAssignment(ID_3_VALUE, ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteAssignment() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGNMENT, DELETE, TEACHER, ID_5_VALUE, CATHEDRA, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).deleteAssignment(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.deleteAssignment(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}