package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.dto.TeacherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import com.github.googelfist.university.entities.Cathedra;
import com.github.googelfist.university.entities.Teacher;
import com.github.googelfist.university.repository.TeacherRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static com.github.googelfist.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TeacherServiceImplTest {
    @Mock
    private TeacherRepository mockTeacherRepository;

    private TeacherService teacherService;
    private Teacher teacher;
    private TeacherDto teacherDto;
    private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        Cathedra cathedra = new Cathedra();
        cathedra.setId(ID_1_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        teacherService = new TeacherServiceImpl(mockTeacherRepository);

        teacher = new Teacher();
        teacher.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacher.setLastName(TEACHER_1_LAST_NAME_VALUE);
        teacher.setPhone(TEACHER_1_PHONE_VALUE);
        teacher.setQualification(QUALIFICATION_1_VALUE);
        teacher.setCathedra(cathedra);

        teacherDto = new TeacherDto();
        teacherDto.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDto.setLastName(TEACHER_1_LAST_NAME_VALUE);
        teacherDto.setPhone(TEACHER_1_PHONE_VALUE);
        teacherDto.setQualification(QUALIFICATION_1_VALUE);
        teacherDto.setCathedraId(ID_1_VALUE);
        teacherDto.setCathedraName(CATHEDRA_1_NAME_VALUE);

        teachers = singletonList(teacher);
    }

    @Test
    void shouldCallCreateTeacher() {
        teacherService.create(teacher);

        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(teacher);
    }

    @Test
    void shouldCallGetTeacherByID() {
        when(mockTeacherRepository.findById(ID_1_VALUE)).thenReturn(java.util.Optional.ofNullable(teacher));

        Teacher actualTeacher = teacherService.getById(ID_1_VALUE);

        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(teacher, actualTeacher);
    }

    @Test
    void shouldCallGetAllTeachers() {
        when(mockTeacherRepository.findAll()).thenReturn(teachers);
        List<Teacher> actualTeachers = teacherService.getAll();

        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void shouldCallUpdateTeacher() {
        teacherService.update(teacher);

        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(teacher);
    }

    @Test
    void shouldCallDeleteTeacher() {
        teacherService.delete(ID_1_VALUE);

        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldCallGetAllDtoTeachersPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Teacher> teacherPage = new PageImpl<>(teachers, pageable, teachers.size());
        when(mockTeacherRepository.findAll(pageable)).thenReturn(teacherPage);

        List<TeacherDto> teachersDto = new ArrayList<>();
        teachersDto.add(teacherDto);
        Page<TeacherDto> expectedTeachersDtoPage = new PageImpl<>(teachersDto, pageable, teachersDto.size());

        Page<TeacherDto> actualTeachersDtoPage = teacherService.getAllDto(pageable);

        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedTeachersDtoPage, actualTeachersDtoPage);
    }

    @Test
    void shouldCallGetAllDtoTeachersByCathedraIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Teacher> teacherPage = new PageImpl<>(teachers, pageable, teachers.size());
        when(mockTeacherRepository.findByCathedraId(ID_1_VALUE, pageable)).thenReturn(teacherPage);

        List<TeacherDto> teachersDto = new ArrayList<>();
        teachersDto.add(teacherDto);
        Page<TeacherDto> expectedTeachersDtoPage = new PageImpl<>(teachersDto, pageable, teachersDto.size());

        Page<TeacherDto> actualTeachersDtoPage = teacherService.getDtoByCathedraId(ID_1_VALUE, pageable);

        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByCathedraId(ID_1_VALUE, pageable);
        assertEquals(expectedTeachersDtoPage, actualTeachersDtoPage);
    }

    @Test
    void shouldCallGetDtoTeacherById() {
        when(mockTeacherRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(teacher));
        TeacherDto actualTeacherDto = teacherService.getDtoById(ID_1_VALUE);
        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);

        assertEquals(teacherDto, actualTeacherDto);
    }

    @Test
    void shouldCallCreateDtoTeacher() {
        teacherService.createDto(teacherDto);
        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(teacher);
    }

    @Test
    void shouldCallUpdateDtoTeacher() {
        teacherService.updateDto(teacherDto);
        verify(mockTeacherRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(teacher);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCantGetByTeacherId() {
        String message = format(ENTITY_NOT_FOUND, TEACHER);
        doThrow(new EntityNotFoundException(message)).when(mockTeacherRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> mockTeacherRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}