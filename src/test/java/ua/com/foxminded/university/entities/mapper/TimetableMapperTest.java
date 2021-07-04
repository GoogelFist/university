package ua.com.foxminded.university.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.entities.Timetable;
import ua.com.foxminded.university.entities.dto.TimetableDto;
import ua.com.foxminded.university.entities.dto.TimetableDtoRequest;
import ua.com.foxminded.university.entities.dto.TimetableDtoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

class TimetableMapperTest {
    private final Timetable timetable = new Timetable();
    private final TimetableDto timetableDto = new TimetableDto();
    private final TimetableDtoResponse timetableDtoResponse = new TimetableDtoResponse();
    private final TimetableDtoRequest timetableDtoRequest = new TimetableDtoRequest();
    private final Group group = new Group();
    private final Teacher teacher = new Teacher();

    @BeforeEach
    void setUp() {
        teacher.setId(ID_1_VALUE);
        teacher.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacher.setLastName(TEACHER_1_LAST_NAME_VALUE);

        group.setId(ID_1_VALUE);
        group.setName(GROUP_1_NAME_VALUE);

        timetable.setId(ID_0_VALUE);
        timetable.setDate(DATE_1_VALUE);
        timetable.setStartTime(TIME_1_VALUE);
        timetable.setLectureHall(LECTURE_HALL_1_VALUE);
        timetable.setSubject(SUBJECT_1_VALUE);
        timetable.setGroup(group);
        timetable.setTeacher(teacher);

        timetableDto.setDate(DATE_1_VALUE);
        timetableDto.setStartTime(TIME_1_VALUE);
        timetableDto.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDto.setSubject(SUBJECT_1_VALUE);
        timetableDto.setGroupId(ID_1_VALUE);
        timetableDto.setGroupName(GROUP_1_NAME_VALUE);
        timetableDto.setTeacherId(ID_1_VALUE);
        timetableDto.setTeacherFirstName(TEACHER_1_FIRST_NAME_VALUE);
        timetableDto.setTeacherLastName(TEACHER_1_LAST_NAME_VALUE);

        timetableDtoResponse.setDate(DATE_1_VALUE);
        timetableDtoResponse.setStartTime(TIME_1_VALUE);
        timetableDtoResponse.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDtoResponse.setSubject(SUBJECT_1_VALUE);
        timetableDtoResponse.setGroupId(ID_1_VALUE);
        timetableDtoResponse.setGroupName(GROUP_1_NAME_VALUE);
        timetableDtoResponse.setTeacherId(ID_1_VALUE);
        timetableDtoResponse.setTeacherFirstName(TEACHER_1_FIRST_NAME_VALUE);
        timetableDtoResponse.setTeacherLastName(TEACHER_1_LAST_NAME_VALUE);

        timetableDtoRequest.setDate(DATE_1_VALUE);
        timetableDtoRequest.setStartTime(TIME_1_VALUE);
        timetableDtoRequest.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDtoRequest.setSubject(SUBJECT_1_VALUE);
        timetableDtoRequest.setGroupId(ID_1_VALUE);
        timetableDtoRequest.setTeacherId(ID_1_VALUE);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToTimetableDtoResponse() {
        TimetableDtoResponse actualTimetableDtoResponse = TimetableMapper.INSTANCE.toTimetableDtoResponse(timetable);
        assertEquals(timetableDtoResponse, actualTimetableDtoResponse);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToTimetableEntityFromDtoRequest() {
        Timetable actualTimetable = TimetableMapper.INSTANCE.toTimetableEntity(timetableDtoRequest);
        assertEquals(timetable, actualTimetable);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToTimetableDto() {
        TimetableDto actualTimetableDto = TimetableMapper.INSTANCE.toTimetableDto(timetable);
        assertEquals(timetableDto, actualTimetableDto);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToTimetableEntityFromDto() {
        Timetable actualTimetable = TimetableMapper.INSTANCE.toTimetableEntity(timetableDto);
        assertEquals(timetable, actualTimetable);
    }
}