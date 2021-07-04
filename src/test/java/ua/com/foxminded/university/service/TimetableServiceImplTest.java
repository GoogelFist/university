package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.com.foxminded.university.entities.Timetable;
import ua.com.foxminded.university.entities.dto.TimetableDto;
import ua.com.foxminded.university.repository.TimetableRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TimetableServiceImplTest {
    @Mock
    private TimetableRepository mockTimetableRepository;

    private TimetableService timetableService;
    private Timetable timetable;
    private TimetableDto timetableDto;
    private List<Timetable> timetables;

    @BeforeEach
    void setUp() {
        timetableService = new TimetableServiceImpl(mockTimetableRepository);

        timetable = new Timetable();
        timetable.setDate(DATE_1_VALUE);
        timetable.setStartTime(TIME_1_VALUE);
        timetable.setLectureHall(LECTURE_HALL_1_VALUE);
        timetable.setSubject(SUBJECT_1_VALUE);

        timetableDto = new TimetableDto();
        timetableDto.setDate(DATE_1_VALUE);
        timetableDto.setStartTime(TIME_1_VALUE);
        timetableDto.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDto.setSubject(SUBJECT_1_VALUE);
        timetableDto.setGroupId(ID_1_VALUE);
        timetableDto.setGroupName(GROUP_1_NAME_VALUE);
        timetableDto.setTeacherId(ID_1_VALUE);
        timetableDto.setTeacherFirstName(TEACHER_1_FIRST_NAME_VALUE);
        timetableDto.setTeacherLastName(TEACHER_1_LAST_NAME_VALUE);

        timetables = singletonList(timetable);
    }

    @Test
    void shouldCallCreateTimetable() {
        timetableService.create(timetable);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldCallGetTimetableById() {
        when(mockTimetableRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(timetable));

        Timetable actualTimetable = timetableService.getById(ID_1_VALUE);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(timetable, actualTimetable);
    }

    @Test
    void shouldCallGetAllTimetables() {
        when(mockTimetableRepository.findAll()).thenReturn(timetables);
        List<Timetable> actualTimetables = timetableService.getAll();

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(timetables, actualTimetables);
    }

    @Test
    void shouldCallUpdateTimetable() {
        timetableService.update(timetable);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldCallDeleteTimetable() {
        timetableService.delete(ID_1_VALUE);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldCallGetAllDtoTimetables() {
        when(mockTimetableRepository.findAll()).thenReturn(timetables);
        timetableService.getAllDto();
        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
    }

    @Test
    void shouldCallGetDtoTimetablesById() {
        when(mockTimetableRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(timetable));
        timetableService.getDtoById(ID_1_VALUE);
        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
    }

    @Test
    void shouldCallCreateDtoTimetable() {
        timetableService.createDto(timetableDto);
        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldCallUpdateDtoTimetable() {
        timetableService.updateDto(timetableDto);
        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCantGetByTimetableId() {
        String message = format(ENTITY_NOT_FOUND, TIMETABLE);
        doThrow(new EntityNotFoundException(message)).when(mockTimetableRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> mockTimetableRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}