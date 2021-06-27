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
import ua.com.foxminded.university.dao.TeacherDAO;
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

    private TeacherService teacherService;
    private Teacher teacher;
    private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherServiceImpl(mockTeacherDAO);
        teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE);
        teachers = singletonList(teacher);
    }

    @Test
    void shouldCallCreateTeacher() {
        teacherService.create(teacher);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(teacher);
    }

    @Test
    void shouldCallGetTeacherByID() {
        when(mockTeacherDAO.getById(ID_1_VALUE)).thenReturn(teacher);
        Teacher actualTeacher = teacherService.getById(ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(teacher, actualTeacher);
    }

    @Test
    void shouldCallGetAllTeachers() {
        when(mockTeacherDAO.getAll()).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getAll();

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallGetAllTeachersPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        teachers = new ArrayList<>();
        teachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE));
        when(mockTeacherDAO.getAll()).thenReturn(teachers);

        Page<Teacher> expectedPageStudents = new PageImpl<>(teachers, pageable, teachers.size());
        Page<Teacher> actualPageStudents = teacherService.getAll(pageable);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallUpdateTeacher() {
        teacherService.update(teacher);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(teacher);
    }

    @Test
    void shouldCallDeleteTeacher() {
        teacherService.delete(ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetTeachersByCathedraID() {
        when(mockTeacherDAO.getByCathedraId(ID_1_VALUE)).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getByCathedraId(ID_1_VALUE);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallGetTeachersByCathedraIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        teachers = new ArrayList<>();
        teachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE));
        when(mockTeacherDAO.getByCathedraId(ID_1_VALUE)).thenReturn(teachers);

        Page<Teacher> expectedPageStudents = new PageImpl<>(teachers, pageable, teachers.size());
        Page<Teacher> actualPageStudents = teacherService.getByCathedraId(ID_1_VALUE, pageable);

        verify(mockTeacherDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByTeacherId() {
        String message = format(ENTITY_NOT_FOUND, TEACHER);
        doThrow(new ServiceException(message)).when(mockTeacherDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}