package ua.com.foxminded.university.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.dto.StudentDtoRequest;
import ua.com.foxminded.university.entities.dto.StudentDtoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

class StudentMapperTest {
    private final Student student = new Student();
    private final StudentDtoResponse studentDtoResponse = new StudentDtoResponse();
    private final StudentDtoRequest studentDtoRequest = new StudentDtoRequest();


    @BeforeEach
    void setUp() {
        Group group = new Group();
        group.setId(ID_1_VALUE);
        group.setName(GROUP_1_NAME_VALUE);

        student.setId(ID_0_VALUE);
        student.setFirstName(STUDENT_1_FIRST_NAME_VALUE);
        student.setLastName(STUDENT_1_LAST_NAME_VALUE);
        student.setPhone(STUDENT_1_PHONE_VALUE);
        student.setGroup(group);

        studentDtoResponse.setId(ID_0_VALUE);
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
}