package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.entities.MonthTimetable;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class MonthTimetableServiceImplTest {
    @Mock
    private MonthTimetableDAO mockMonthTimetableDAO;
    @Mock
    private DayTimeTableService mockDayTimeTableService;

    private MonthTimetableService monthTimetableService;
    private MonthTimetable monthTimetable;
    private List<MonthTimetable> monthTimetables;

    @BeforeEach
    void setUp() {
        monthTimetableService = new MonthTimetableServiceImpl(mockMonthTimetableDAO, mockDayTimeTableService);
        monthTimetable = new MonthTimetable(1, LocalDate.of(2021, 4, 23));
        monthTimetables = singletonList(monthTimetable);
    }

    @Test
    void shouldCallCreateMonthTimetable() {
        monthTimetableService.create(monthTimetable);

        verify(mockMonthTimetableDAO, times(1)).create(monthTimetable);
    }

    @Test
    void shouldCallGetByMonthTimetableId() {
        when(mockMonthTimetableDAO.getById(1)).thenReturn(monthTimetable);
        MonthTimetable actualMonthTimetable = monthTimetableService.getById(1);

        verify(mockMonthTimetableDAO, times(1)).getById(1);
        verify(mockDayTimeTableService, times(1)).getByMonthTimetableId(1);
        assertEquals(monthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldCallGetAllMonthTimetables() {
        when(mockMonthTimetableDAO.getAll()).thenReturn(monthTimetables);
        List<MonthTimetable> actualMonthTimetables = monthTimetableService.getAll();

        verify(mockMonthTimetableDAO, times(1)).getAll();
        verify(mockDayTimeTableService, times(1)).getByMonthTimetableId(1);
        assertEquals(monthTimetables, actualMonthTimetables);
    }

    @Test
    void shouldCallUpdateMonthTimetable() {
        monthTimetableService.update(1, monthTimetable);

        verify(mockMonthTimetableDAO, times(1)).update(1, monthTimetable);
    }

    @Test
    void shouldCallDeleteMonthTimetable() {
        monthTimetableService.delete(1);

        verify(mockMonthTimetableDAO, times(1)).delete(1);
    }
}