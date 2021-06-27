package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DayTimetableDAO;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class DayTimetableServiceImplTest {
    @Mock
    private DayTimetableDAO mockDayTimetableDAO;

    private DayTimeTableService dayTimeTableService;
    private DayTimetable dayTimetable;
    private List<DayTimetable> dayTimetables;

    @BeforeEach
    void setUp() {
        dayTimeTableService = new DayTimetableServiceImpl(mockDayTimetableDAO);
        dayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE);
        dayTimetables = singletonList(dayTimetable);
    }

    @Test
    void shouldCallCreateDayTimetable() {
        dayTimeTableService.create(dayTimetable);

        verify(mockDayTimetableDAO, times(ID_1_VALUE)).create(dayTimetable);
    }

    @Test
    void shouldCallGetDayTimetableByID() {
        when(mockDayTimetableDAO.getById(ID_1_VALUE)).thenReturn(dayTimetable);
        DayTimetable actualTimetable = dayTimeTableService.getById(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(dayTimetable, actualTimetable);
    }

    @Test
    void shouldCallGetAllTimetables() {
        when(mockDayTimetableDAO.getAll()).thenReturn(dayTimetables);
        List<DayTimetable> actualTimetables = dayTimeTableService.getAll();

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(dayTimetables, actualTimetables);
    }

    @Test
    void shouldCallGetAllDayTimetablesPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE));
        when(mockDayTimetableDAO.getAll()).thenReturn(dayTimetables);

        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());
        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getAll(pageable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldCallUpdateTimetable() {
        dayTimeTableService.update(dayTimetable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(dayTimetable);
    }

    @Test
    void shouldCallDeleteTimetable() {
        dayTimeTableService.delete(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetDayTimetableByMonthTimetableId() {
        when(mockDayTimetableDAO.getByMonthTimetableId(ID_1_VALUE)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByMonthTimetableId(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByMonthTimetableId(ID_1_VALUE);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetablesByMonthTimetableIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE));
        when(mockDayTimetableDAO.getByMonthTimetableId(ID_1_VALUE)).thenReturn(dayTimetables);

        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());
        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getByMonthTimetableId(ID_1_VALUE, pageable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByMonthTimetableId(ID_1_VALUE);
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }
}