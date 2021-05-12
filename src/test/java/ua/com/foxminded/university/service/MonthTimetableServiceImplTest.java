package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldCallCreateMonthTimetable() throws DaoException {
        monthTimetableService.create(monthTimetable);

        verify(mockMonthTimetableDAO, times(1)).create(monthTimetable);
    }

    @Test
    void shouldCallGetByMonthTimetableId() throws DaoException {
        when(mockMonthTimetableDAO.getById(1)).thenReturn(monthTimetable);
        MonthTimetable actualMonthTimetable = monthTimetableService.getById(1);

        verify(mockMonthTimetableDAO, times(1)).getById(1);
        verify(mockDayTimeTableService, times(1)).getByMonthTimetableId(1);
        assertEquals(monthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldCallGetAllMonthTimetables() throws DaoException {
        when(mockMonthTimetableDAO.getAll()).thenReturn(monthTimetables);
        List<MonthTimetable> actualMonthTimetables = monthTimetableService.getAll();

        verify(mockMonthTimetableDAO, times(1)).getAll();
        verify(mockDayTimeTableService, times(1)).getByMonthTimetableId(1);
        assertEquals(monthTimetables, actualMonthTimetables);
    }

    @Test
    void shouldCallUpdateMonthTimetable() throws DaoException {
        monthTimetableService.update(1, monthTimetable);

        verify(mockMonthTimetableDAO, times(1)).update(1, monthTimetable);
    }

    @Test
    void shouldCallDeleteMonthTimetable() throws DaoException {
        monthTimetableService.delete(1);

        verify(mockMonthTimetableDAO, times(1)).delete(1);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateMonthTimetable() throws DaoException {
        doThrow(new ServiceException("Unable to create monthTimetable")).when(mockMonthTimetableDAO).create(monthTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.create(monthTimetable));
        String actual = exception.getMessage();
        String expected = "Unable to create monthTimetable";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByMonthTimetableId() throws DaoException {
        doThrow(new ServiceException("Unable to get monthTimetable with ID 5")).when(mockMonthTimetableDAO).getById(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.getById(5));
        String actual = exception.getMessage();
        String expected = "Unable to get monthTimetable with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllMonthTimetables() throws DaoException {
        doThrow(new ServiceException("Unable to get all monthTimetables")).when(mockMonthTimetableDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.getAll());
        String actual = exception.getMessage();
        String expected = "Unable to get all monthTimetables";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateMonthTimetable() throws DaoException {
        doThrow(new ServiceException("Unable to update monthTimetable with ID 5")).when(mockMonthTimetableDAO).update(5, monthTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.update(5, monthTimetable));
        String actual = exception.getMessage();
        String expected = "Unable to update monthTimetable with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteMonthTimetable() throws DaoException {
        doThrow(new ServiceException("Unable to delete monthTimetable with ID 5")).when(mockMonthTimetableDAO).delete(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.delete(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete monthTimetable with ID 5";

        assertEquals(expected, actual);
    }
}