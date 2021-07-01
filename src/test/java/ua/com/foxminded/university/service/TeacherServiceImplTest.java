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
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.repository.TeacherRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TeacherServiceImplTest {
    @Mock
    private TeacherRepository mockTeacherRepositoty;

    private TeacherService teacherService;
    private Teacher teacher;
    private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherServiceImpl(mockTeacherRepositoty);
        teacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE);
        teachers = singletonList(teacher);
    }

    @Test
    void shouldCallCreateTeacher() {
        teacherService.create(teacher);

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).save(teacher);
    }

    @Test
    void shouldCallGetTeacherByID() {
        when(mockTeacherRepositoty.findById(ID_1_VALUE)).thenReturn(java.util.Optional.ofNullable(teacher));

        Teacher actualTeacher = teacherService.getById(ID_1_VALUE);

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(teacher, actualTeacher);
    }

    @Test
    void shouldCallGetAllTeachers() {
        when(mockTeacherRepositoty.findAll()).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getAll();

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallGetAllTeachersPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        teachers = new ArrayList<>();
        teachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE));
        Page<Teacher> expectedPageTeachers = new PageImpl<>(teachers, pageable, teachers.size());

        when(mockTeacherRepositoty.findAll(pageable)).thenReturn(expectedPageTeachers);

        Page<Teacher> actualPageStudents = teacherService.getAll(pageable);

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedPageTeachers, actualPageStudents);
    }

    @Test
    void shouldCallUpdateTeacher() {
        teacherService.update(teacher);

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).save(teacher);
    }

    @Test
    void shouldCallDeleteTeacher() {
        teacherService.delete(ID_1_VALUE);

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldCallGetTeachersByCathedraID() {
        when(mockTeacherRepositoty.findByCathedraId(ID_1_VALUE)).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getByCathedraId(ID_1_VALUE);

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).findByCathedraId(ID_1_VALUE);
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallGetTeachersByCathedraIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        teachers = new ArrayList<>();
        teachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE));
        Page<Teacher> expectedPageTeachers = new PageImpl<>(teachers, pageable, teachers.size());

        when(mockTeacherRepositoty.findByCathedraId(ID_1_VALUE, pageable)).thenReturn(expectedPageTeachers);


        Page<Teacher> actualPageStudents = teacherService.getByCathedraId(ID_1_VALUE, pageable);

        verify(mockTeacherRepositoty, times(NUMBER_OF_INVOCATIONS_VALUE)).findByCathedraId(ID_1_VALUE, pageable);
        assertEquals(expectedPageTeachers, actualPageStudents);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByTeacherId() {
        String message = format(ENTITY_NOT_FOUND, TEACHER);
        doThrow(new ServiceException(message)).when(mockTeacherRepositoty).findById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockTeacherRepositoty.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}