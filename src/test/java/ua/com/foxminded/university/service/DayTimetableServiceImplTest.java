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
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.repository.DayTimetableRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class DayTimetableServiceImplTest {
    @Mock
    private DayTimetableRepository mockDayTimetableRepository;

    private DayTimeTableService dayTimeTableService;
    private DayTimetable dayTimetable;
    private List<DayTimetable> dayTimetables;

    @BeforeEach
    void setUp() {
        dayTimeTableService = new DayTimetableServiceImpl(mockDayTimetableRepository);
        dayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE);
        dayTimetables = singletonList(dayTimetable);
    }

    @Test
    void shouldCallCreateDayTimetable() {
        dayTimeTableService.create(dayTimetable);

        verify(mockDayTimetableRepository, times(ID_1_VALUE)).save(dayTimetable);
    }

    @Test
    void shouldCallGetDayTimetableByID() {
        when(mockDayTimetableRepository.findById(ID_1_VALUE)).thenReturn(java.util.Optional.ofNullable(dayTimetable));
        DayTimetable actualTimetable = dayTimeTableService.getById(ID_1_VALUE);

        verify(mockDayTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(dayTimetable, actualTimetable);
    }

    @Test
    void shouldCallGetAllTimetables() {
        when(mockDayTimetableRepository.findAll()).thenReturn(dayTimetables);
        List<DayTimetable> actualTimetables = dayTimeTableService.getAll();

        verify(mockDayTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(dayTimetables, actualTimetables);
    }

    @Test
    void shouldCallGetAllDayTimetablesPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE));
        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());

        when(mockDayTimetableRepository.findAll(pageable)).thenReturn(expectedPageDayTimetable);

        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getAll(pageable);

        verify(mockDayTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldCallUpdateTimetable() {
        dayTimeTableService.update(dayTimetable);

        verify(mockDayTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(dayTimetable);
    }

    @Test
    void shouldCallDeleteTimetable() {
        dayTimeTableService.delete(ID_1_VALUE);

        verify(mockDayTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldCallGetDayTimetableByMonthTimetableId() {
        when(mockDayTimetableRepository.findByMonthTimetableId(ID_1_VALUE)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByMonthTimetableId(ID_1_VALUE);

        verify(mockDayTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByMonthTimetableId(ID_1_VALUE);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetablesByMonthTimetableIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE));
        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());

        when(mockDayTimetableRepository.findByMonthTimetableId(ID_1_VALUE, pageable)).thenReturn(expectedPageDayTimetable);

        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getByMonthTimetableId(ID_1_VALUE, pageable);

        verify(mockDayTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByMonthTimetableId(ID_1_VALUE, pageable);
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }
}