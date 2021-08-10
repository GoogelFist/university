package com.github.googelfist.university.entities.mapper;

import com.github.googelfist.university.entities.dto.rest.StudentDtoRequest;
import com.github.googelfist.university.entities.dto.rest.StudentDtoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.entities.Student;
import com.github.googelfist.university.entities.dto.StudentDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.googelfist.university.utils.Constants.*;

class StudentMapperTest {
    private final Student student = new Student();
    private final StudentDto studentDto = new StudentDto();
    private final StudentDtoResponse studentDtoResponse = new StudentDtoResponse();
    private final StudentDtoRequest studentDtoRequest = new StudentDtoRequest();


    @BeforeEach
    void setUp() {
        Group group = new Group();
        group.setId(ID_1_VALUE);
        group.setName(GROUP_1_NAME_VALUE);

        student.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        student.setLastName(STUDENT_1_LAST_NAME_VALUE);
        student.setPhone(STUDENT_1_PHONE_VALUE);
        student.setGroup(group);

        studentDto.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDto.setLastName(STUDENT_1_LAST_NAME_VALUE);
        studentDto.setPhone(STUDENT_1_PHONE_VALUE);
        studentDto.setGroupId(ID_1_VALUE);
        studentDto.setGroupName(GROUP_1_NAME_VALUE);

        studentDtoResponse.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDtoResponse.setLastName(STUDENT_1_LAST_NAME_VALUE);
        studentDtoResponse.setPhone(STUDENT_1_PHONE_VALUE);
        studentDtoResponse.setGroupId(ID_1_VALUE);
        studentDtoResponse.setGroupName(GROUP_1_NAME_VALUE);

        studentDtoRequest.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        studentDtoRequest.setLastName(STUDENT_1_LAST_NAME_VALUE);
        studentDtoRequest.setPhone(STUDENT_1_PHONE_VALUE);
        studentDtoRequest.setGroupId(ID_1_VALUE);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToStudentDtoResponse() {
        StudentDtoResponse actualStudentDtoResponse = StudentMapper.INSTANCE.toStudentDtoResponse(student);
        assertEquals(studentDtoResponse, actualStudentDtoResponse);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToStudentEntity() {
        Student actualStudent = StudentMapper.INSTANCE.toStudentEntity(studentDtoRequest);
        assertEquals(student, actualStudent);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToStudentDto() {
        StudentDto actualStudentDto = StudentMapper.INSTANCE.toStudentDto(student);
        assertEquals(studentDto, actualStudentDto);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToStudentEntityFromDto() {
        Student actualStudent = StudentMapper.INSTANCE.toStudentEntity(studentDto);
        assertEquals(student, actualStudent);
    }
}