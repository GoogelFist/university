package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.TeacherDAO;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        teacher = new Teacher(1, "David", "Denver", "12345", "1");
        teachers = singletonList(teacher);
    }

    @Test
    void shouldCallCreateTeacher() {
        teacherService.create(teacher);

        verify(mockTeacherDAO, times(1)).create(teacher);
    }

    @Test
    void shouldCallGetTeacherByID() {
        when(mockTeacherDAO.getById(1)).thenReturn(teacher);
        Teacher actualTeacher = teacherService.getById(1);

        verify(mockTeacherDAO, times(1)).getById(1);
        assertEquals(teacher, actualTeacher);
    }

    @Test
    void shouldCallGetAllTeachers() {
        when(mockTeacherDAO.getAll()).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getAll();

        verify(mockTeacherDAO, times(1)).getAll();
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallUpdateTeacher() {
        teacherService.update(1, teacher);

        verify(mockTeacherDAO, times(1)).update(1, teacher);
    }

    @Test
    void shouldCallDeleteTeacher() {
        teacherService.delete(1);

        verify(mockTeacherDAO, times(1)).delete(1);
    }

    @Test
    void shouldCallGetTeachersByCathedraID() {
        when(mockTeacherDAO.getByCathedraId(1)).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getByCathedraId(1);

        verify(mockTeacherDAO, times(1)).getByCathedraId(1);
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallAssignTeacherToCathedra() {
        teacherService.assignToCathedra(1, 1);

        verify(mockTeacherDAO, times(1)).assignToCathedra(1, 1);
    }

    @Test
    void shouldCallUpdateAssignmentTeacherToCathedra() {
        teacherService.updateAssignment(1, 1);

        verify(mockTeacherDAO, times(1)).updateAssignment(1, 1);
    }

    @Test
    void shouldCallDeleteAssignmentTeacherToCathedra() {
        teacherService.deleteAssignment(1);

        verify(mockTeacherDAO, times(1)).deleteAssignment(1);
    }
}