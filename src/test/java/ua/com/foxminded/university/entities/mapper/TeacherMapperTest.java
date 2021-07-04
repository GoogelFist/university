package ua.com.foxminded.university.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.entities.dto.TeacherDtoRequest;
import ua.com.foxminded.university.entities.dto.TeacherDtoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

class TeacherMapperTest {
    private final Teacher teacher = new Teacher();
    private final TeacherDtoResponse teacherDtoResponse = new TeacherDtoResponse();
    private final TeacherDtoRequest teacherDtoRequest = new TeacherDtoRequest();

    @BeforeEach
    void setUp() {
        Cathedra cathedra = new Cathedra();
        cathedra.setId(ID_1_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        teacher.setId(ID_0_VALUE);
        teacher.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacher.setLastName(TEACHER_1_LAST_NAME_VALUE);
        teacher.setPhone(TEACHER_1_PHONE_VALUE);
        teacher.setQualification(QUALIFICATION_1_VALUE);
        teacher.setCathedra(cathedra);

        teacherDtoResponse.setId(ID_0_VALUE);
        teacherDtoResponse.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDtoResponse.setLastName(TEACHER_1_LAST_NAME_VALUE);
        teacherDtoResponse.setPhone(TEACHER_1_PHONE_VALUE);
        teacherDtoResponse.setQualification(QUALIFICATION_1_VALUE);
        teacherDtoResponse.setCathedraId(ID_1_VALUE);
        teacherDtoResponse.setCathedraName(CATHEDRA_1_NAME_VALUE);

        teacherDtoRequest.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDtoRequest.setLastName(TEACHER_1_LAST_NAME_VALUE);
        teacherDtoRequest.setPhone(TEACHER_1_PHONE_VALUE);
        teacherDtoRequest.setQualification(QUALIFICATION_1_VALUE);
        teacherDtoRequest.setCathedraId(ID_1_VALUE);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToTeacherDtoResponse() {
        TeacherDtoResponse actualTeacherDtoResponse = TeacherMapper.INSTANCE.toTeacherDtoResponse(teacher);
        assertEquals(teacherDtoResponse, actualTeacherDtoResponse);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToTeacherEntity() {
        Teacher actualTeacher = TeacherMapper.INSTANCE.toTeacherEntity(teacherDtoRequest);
        assertEquals(teacher, actualTeacher);
    }
}