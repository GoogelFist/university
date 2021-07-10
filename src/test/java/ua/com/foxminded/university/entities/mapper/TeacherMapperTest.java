package ua.com.foxminded.university.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.entities.dto.TeacherDto;
import ua.com.foxminded.university.entities.dto.rest.TeacherDtoRequest;
import ua.com.foxminded.university.entities.dto.rest.TeacherDtoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

class TeacherMapperTest {
    private final Teacher teacher = new Teacher();
    private final TeacherDto teacherDto = new TeacherDto();
    private final TeacherDtoResponse teacherDtoResponse = new TeacherDtoResponse();
    private final TeacherDtoRequest teacherDtoRequest = new TeacherDtoRequest();
    private final Cathedra cathedra = new Cathedra();

    @BeforeEach
    void setUp() {
        cathedra.setId(ID_1_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        teacher.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacher.setLastName(TEACHER_1_LAST_NAME_VALUE);
        teacher.setPhone(TEACHER_1_PHONE_VALUE);
        teacher.setQualification(QUALIFICATION_1_VALUE);
        teacher.setCathedra(cathedra);

        teacherDto.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacherDto.setLastName(TEACHER_1_LAST_NAME_VALUE);
        teacherDto.setPhone(TEACHER_1_PHONE_VALUE);
        teacherDto.setQualification(QUALIFICATION_1_VALUE);
        teacherDto.setCathedraId(ID_1_VALUE);
        teacherDto.setCathedraName(CATHEDRA_1_NAME_VALUE);

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

    @Test
    void shouldReturnCorrectDtoWhenConvertToTeacherDto() {
        TeacherDto actualTeacherDto = TeacherMapper.INSTANCE.toTeacherDto(teacher);
        assertEquals(teacherDto, actualTeacherDto);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToTeacherEntityFromDto() {
        Teacher actualTeacher = TeacherMapper.INSTANCE.toTeacherEntity(teacherDto);
        assertEquals(teacher, actualTeacher);
    }
}