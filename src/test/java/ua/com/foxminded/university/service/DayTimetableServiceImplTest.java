package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DayTimetableDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class DayTimetableServiceImplTest {
    @Mock
    private DayTimetableDAO mockDayTimetable;

    @Mock
    private GroupService mockGroupService;

    @Mock
    private TeacherService mockTeacherService;

    private DayTimeTableService dayTimeTableService;
    private DayTimetable dayTimetable;
    private List<DayTimetable> dayTimetables;

    @BeforeEach
    void setUp() {
        dayTimeTableService = new DayTimetableServiceImpl(mockDayTimetable, mockGroupService, mockTeacherService);
        dayTimetable = new DayTimetable(1, LocalTime.of(8, 0), "112", "math", new Group(1), new Teacher(1));
        dayTimetables = singletonList(dayTimetable);
    }

    @Test
    void shouldCallCreateDayTimetable() throws DaoException {
        dayTimeTableService.create(dayTimetable);

        verify(mockDayTimetable, times(1)).create(dayTimetable);
    }

    @Test
    void shouldCallGetDayTimetableByID() throws DaoException {
        when(mockDayTimetable.getById(1)).thenReturn(dayTimetable);
        DayTimetable actualTimetable = dayTimeTableService.getById(1);

        verify(mockDayTimetable, times(1)).getById(1);
        verify(mockGroupService, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetable, actualTimetable);
    }

    @Test
    void shouldCallGetAllTimetables() throws DaoException {
        when(mockDayTimetable.getAll()).thenReturn(dayTimetables);
        List<DayTimetable> actualTimetables = dayTimeTableService.getAll();

        verify(mockDayTimetable, times(1)).getAll();
        verify(mockGroupService, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetables, actualTimetables);
    }

    @Test
    void shouldCallUpdateTimetable() throws DaoException {
        dayTimeTableService.update(1, dayTimetable);

        verify(mockDayTimetable, times(1)).update(1, dayTimetable);
    }

    @Test
    void shouldCallDeleteTimetable() throws DaoException {
        dayTimeTableService.delete(1);

        verify(mockDayTimetable, times(1)).delete(1);
    }

    @Test
    void shouldCallGetDayTimetableByGroupId() throws DaoException {
        when(mockDayTimetable.getByGroupId(1)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByGroupId(1);

        verify(mockDayTimetable, times(1)).getByGroupId(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetableByTeacherId() throws DaoException {
        when(mockDayTimetable.getByTeacherId(1)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByTeacherId(1);

        verify(mockDayTimetable, times(1)).getByTeacherId(1);
        verify(mockGroupService, times(1)).getById(1);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetableByMonthTimetableId() throws DaoException {
        when(mockDayTimetable.getByMonthTimetableId(1)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByMonthTimetableId(1);

        verify(mockDayTimetable, times(1)).getByMonthTimetableId(1);
        verify(mockGroupService, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateDayTimetable() throws DaoException {
        doThrow(new ServiceException("Unable to create dayTimetable")).when(mockDayTimetable).create(dayTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.create(dayTimetable));
        String actual = exception.getMessage();
        String expected = "Unable to create dayTimetable";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableById() throws DaoException {
        doThrow(new ServiceException("Unable to get dayTimetable with ID 5")).when(mockDayTimetable).getById(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.getById(5));
        String actual = exception.getMessage();
        String expected = "Unable to get dayTimetable with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllDayTimetables() throws DaoException {
        doThrow(new ServiceException("Unable to get all dayTimetables")).when(mockDayTimetable).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.getAll());
        String actual = exception.getMessage();
        String expected = "Unable to get all dayTimetables";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateDayTimetable() throws DaoException {
        doThrow(new ServiceException("Unable to update dayTimetable with ID 5")).when(mockDayTimetable).update(5, dayTimetable);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.update(5, dayTimetable));
        String actual = exception.getMessage();
        String expected = "Unable to update dayTimetable with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteDayTimetable() throws DaoException {
        doThrow(new ServiceException("Unable to delete dayTimetable with ID 5")).when(mockDayTimetable).delete(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.delete(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete dayTimetable with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableByGroupId() throws DaoException {
        doThrow(new ServiceException("Unable to get dayTimetable by groupId 5")).when(mockDayTimetable).getByGroupId(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.getByGroupId(5));
        String actual = exception.getMessage();
        String expected = "Unable to get dayTimetable by groupId 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableByTeacherId() throws DaoException {
        doThrow(new ServiceException("Unable to get dayTimetable by teacherId 5")).when(mockDayTimetable).getByTeacherId(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.getByTeacherId(5));
        String actual = exception.getMessage();
        String expected = "Unable to get dayTimetable by teacherId 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetDayTimetableByMonthTimetableId() throws DaoException {
        doThrow(new ServiceException("Unable to get dayTimetable by monthTimetableId 5")).when(mockDayTimetable).getByMonthTimetableId(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockDayTimetable.getByMonthTimetableId(5));
        String actual = exception.getMessage();
        String expected = "Unable to get dayTimetable by monthTimetableId 5";

        assertEquals(expected, actual);
    }
}