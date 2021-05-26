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
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

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
        monthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1);
        monthTimetables = singletonList(monthTimetable);
    }

    @Test
    void shouldCallCreateMonthTimetable() throws DaoException {
        monthTimetableService.create(monthTimetable);

        verify(mockMonthTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(monthTimetable);
    }

    @Test
    void shouldCallGetByMonthTimetableId() throws DaoException {
        when(mockMonthTimetableDAO.getById(ID_1_VALUE)).thenReturn(monthTimetable);
        MonthTimetable actualMonthTimetable = monthTimetableService.getById(ID_1_VALUE);

        verify(mockMonthTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockDayTimeTableService, times(NUMBER_OF_INVOCATIONS_VALUE)).getByMonthTimetableId(ID_1_VALUE);
        assertEquals(monthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldCallGetAllMonthTimetables() throws DaoException {
        when(mockMonthTimetableDAO.getAll()).thenReturn(monthTimetables);
        List<MonthTimetable> actualMonthTimetables = monthTimetableService.getAll();

        verify(mockMonthTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        verify(mockDayTimeTableService, times(NUMBER_OF_INVOCATIONS_VALUE)).getByMonthTimetableId(ID_1_VALUE);
        assertEquals(monthTimetables, actualMonthTimetables);
    }

    @Test
    void shouldCallGetAllMonthTimetablesPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        monthTimetables = new ArrayList<>();
        monthTimetables.add(new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1));
        when(mockMonthTimetableDAO.getAll()).thenReturn(monthTimetables);

        Page<MonthTimetable> expectedPageDayTimetable = new PageImpl<>(monthTimetables, pageable, monthTimetables.size());
        Page<MonthTimetable> actualPageDayTimetable = monthTimetableService.getAll(pageable);

        verify(mockMonthTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldCallUpdateMonthTimetable() throws DaoException {
        monthTimetableService.update(ID_1_VALUE, monthTimetable);

        verify(mockMonthTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(ID_1_VALUE, monthTimetable);
    }

    @Test
    void shouldCallDeleteMonthTimetable() throws DaoException {
        monthTimetableService.delete(ID_1_VALUE);

        verify(mockMonthTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateMonthTimetable() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_CREATE, MONTH_TIMETABLE);
        doThrow(new ServiceException(message)).when(mockMonthTimetableDAO).create(monthTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.create(monthTimetable));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByMonthTimetableId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, GET, MONTH_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockMonthTimetableDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllMonthTimetables() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_GET_ALL, MONTH_TIMETABLES);
        doThrow(new ServiceException(message)).when(mockMonthTimetableDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.getAll());
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateMonthTimetable() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, UPDATE, MONTH_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockMonthTimetableDAO).update(ID_5_VALUE, monthTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.update(ID_5_VALUE, monthTimetable));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteMonthTimetable() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, DELETE, MONTH_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockMonthTimetableDAO).delete(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableDAO.delete(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}