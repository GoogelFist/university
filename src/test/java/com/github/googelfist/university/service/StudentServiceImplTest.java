package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.dto.StudentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.entities.Student;
import com.github.googelfist.university.repository.StudentRepository;

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
class StudentServiceImplTest {
    @Mock
    private StudentRepository mockStudentRepository;

    private StudentService studentService;
    private Student student;
    private StudentDto studentDto;
    private List<Student> students;

    @BeforeEach
    void setUp() {
        Group group = new Group();
        group.setId(ID_1_VALUE);
        group.setName(GROUP_1_NAME_VALUE);

        studentService = new StudentServiceImpl(mockStudentRepository);

        student = new Student();
        student.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        student.setLastName(STUDENT_1_LAST_NAME_VALUE);
        student.setPhone(STUDENT_1_PHONE_VALUE);
        student.setGroup(group);

        studentDto = new StudentDto();
        studentDto.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDto.setLastName(STUDENT_1_LAST_NAME_VALUE);
        studentDto.setPhone(STUDENT_1_PHONE_VALUE);
        studentDto.setGroupId(ID_1_VALUE);
        studentDto.setGroupName(GROUP_1_NAME_VALUE);

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
    void shouldCallGetAllDtoStudentsPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());
        when(mockStudentRepository.findAll(pageable)).thenReturn(studentPage);

        List<StudentDto> studentsDto = new ArrayList<>();
        studentsDto.add(studentDto);
        Page<StudentDto> expectedStudentsDtoPage = new PageImpl<>(studentsDto, pageable, studentsDto.size());

        Page<StudentDto> actualStudentsDtoPage = studentService.getAllDto(pageable);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedStudentsDtoPage, actualStudentsDtoPage);
    }

    @Test
    void shouldCallGetAllDtoStudentsByCathedraIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());
        when(mockStudentRepository.findByGroupId(ID_1_VALUE, pageable)).thenReturn(studentPage);

        List<StudentDto> studentsDto = new ArrayList<>();
        studentsDto.add(studentDto);
        Page<StudentDto> expectedStudentsDtoPage = new PageImpl<>(studentsDto, pageable, studentsDto.size());

        Page<StudentDto> actualStudentsDtoPage = studentService.getDtoByGroupId(ID_1_VALUE, pageable);

        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByGroupId(ID_1_VALUE, pageable);
        assertEquals(expectedStudentsDtoPage, actualStudentsDtoPage);
    }

    @Test
    void shouldCallGetDtoStudentById() {
        when(mockStudentRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(student));
        StudentDto actualStudentDto = studentService.getDtoById(ID_1_VALUE);
        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);

        assertEquals(studentDto, actualStudentDto);
    }

    @Test
    void shouldCallCreateDtoStudent() {
        studentService.createDto(studentDto);
        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(student);
    }

    @Test
    void shouldCallUpdateDtoStudent() {
        studentService.updateDto(studentDto);
        verify(mockStudentRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(student);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCantGetByStudentId() {
        String message = format(ENTITY_NOT_FOUND, STUDENT);
        doThrow(new EntityNotFoundException(message)).when(mockStudentRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> mockStudentRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}