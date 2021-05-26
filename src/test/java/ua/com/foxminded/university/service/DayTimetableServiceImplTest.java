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
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.entities.Teacher;
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
class DayTimetableServiceImplTest {
    @Mock
    private DayTimetableDAO mockDayTimetableDAO;

    @Mock
    private GroupService mockGroupService;

    @Mock
    private TeacherService mockTeacherService;

    @Mock
    private MonthTimetableDAO mockMonthTimetableDao;

    private DayTimeTableService dayTimeTableService;
    private DayTimetable dayTimetable;
    private List<DayTimetable> dayTimetables;

    @BeforeEach
    void setUp() {
        dayTimeTableService = new DayTimetableServiceImpl(mockDayTimetableDAO, mockGroupService, mockTeacherService, mockMonthTimetableDao);
        dayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, new Group(ID_1_VALUE), new Teacher(ID_1_VALUE), new MonthTimetable(ID_1_VALUE));
        dayTimetables = singletonList(dayTimetable);
    }

    @Test
    void shouldCallCreateDayTimetable() throws DaoException {
        dayTimeTableService.create(dayTimetable);

        verify(mockDayTimetableDAO, times(ID_1_VALUE)).create(dayTimetable);
    }

    @Test
    void shouldCallGetDayTimetableByID() throws DaoException {
        when(mockDayTimetableDAO.getById(ID_1_VALUE)).thenReturn(dayTimetable);
        DayTimetable actualTimetable = dayTimeTableService.getById(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockGroupService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockTeacherService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(dayTimetable, actualTimetable);
    }

    @Test
    void shouldCallGetAllTimetables() throws DaoException {
        when(mockDayTimetableDAO.getAll()).thenReturn(dayTimetables);
        List<DayTimetable> actualTimetables = dayTimeTableService.getAll();

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        verify(mockGroupService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockTeacherService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(dayTimetables, actualTimetables);
    }

    @Test
    void shouldCallGetAllDayTimetablesPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, new Group(ID_1_VALUE), new Teacher(ID_1_VALUE), new MonthTimetable(ID_1_VALUE)));
        when(mockDayTimetableDAO.getAll()).thenReturn(dayTimetables);

        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());
        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getAll(pageable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldCallUpdateTimetable() throws DaoException {
        dayTimeTableService.update(ID_1_VALUE, dayTimetable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(ID_1_VALUE, dayTimetable);
    }

    @Test
    void shouldCallDeleteTimetable() throws DaoException {
        dayTimeTableService.delete(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetDayTimetableByGroupId() throws DaoException {
        when(mockDayTimetableDAO.getByGroupId(ID_1_VALUE)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByGroupId(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        verify(mockTeacherService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetablesByGroupIdPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, new Group(ID_1_VALUE), new Teacher(ID_1_VALUE), new MonthTimetable(ID_1_VALUE)));
        when(mockDayTimetableDAO.getByGroupId(ID_1_VALUE)).thenReturn(dayTimetables);

        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());
        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getByGroupId(ID_1_VALUE, pageable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldCallGetDayTimetableByTeacherId() throws DaoException {
        when(mockDayTimetableDAO.getByTeacherId(ID_1_VALUE)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByTeacherId(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByTeacherId(ID_1_VALUE);
        verify(mockGroupService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetablesByTeacherIdPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, new Group(ID_1_VALUE), new Teacher(ID_1_VALUE), new MonthTimetable(ID_1_VALUE)));
        when(mockDayTimetableDAO.getByTeacherId(ID_1_VALUE)).thenReturn(dayTimetables);

        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());
        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getByTeacherId(ID_1_VALUE, pageable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByTeacherId(ID_1_VALUE);
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldCallGetDayTimetableByMonthTimetableId() throws DaoException {
        when(mockDayTimetableDAO.getByMonthTimetableId(ID_1_VALUE)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByMonthTimetableId(ID_1_VALUE);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByMonthTimetableId(ID_1_VALUE);
        verify(mockGroupService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockTeacherService, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetablesByMonthTimetableIdPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        dayTimetables = new ArrayList<>();
        dayTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, new Group(ID_1_VALUE), new Teacher(ID_1_VALUE), new MonthTimetable(ID_1_VALUE)));
        when(mockDayTimetableDAO.getByMonthTimetableId(ID_1_VALUE)).thenReturn(dayTimetables);

        Page<DayTimetable> expectedPageDayTimetable = new PageImpl<>(dayTimetables, pageable, dayTimetables.size());
        Page<DayTimetable> actualPageDayTimetable = dayTimeTableService.getByMonthTimetableId(ID_1_VALUE, pageable);

        verify(mockDayTimetableDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByMonthTimetableId(ID_1_VALUE);
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateDayTimetable() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_CREATE, DAY_TIMETABLE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).create(dayTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.create(dayTimetable));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableById() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, GET, DAY_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllDayTimetables() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_GET_ALL, DAY_TIMETABLE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.getAll());
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateDayTimetable() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, UPDATE, DAY_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).update(ID_5_VALUE, dayTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.update(ID_5_VALUE, dayTimetable));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteDayTimetable() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, DELETE, DAY_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).delete(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.delete(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableByGroupId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ENTITY_ID, GET, DAY_TIMETABLE, GROUP, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).getByGroupId(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.getByGroupId(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableByTeacherId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ENTITY_ID, GET, DAY_TIMETABLE, TEACHER, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).getByTeacherId(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.getByTeacherId(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableByMonthTimetableId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ENTITY_ID, GET, DAY_TIMETABLE, MONTH_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockDayTimetableDAO).getByMonthTimetableId(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetableDAO.getByMonthTimetableId(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}