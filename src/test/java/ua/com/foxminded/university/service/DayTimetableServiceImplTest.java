package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DayTimetableDAO;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;

import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void shouldCallCreateDayTimetable() {
        dayTimeTableService.create(dayTimetable);

        verify(mockDayTimetable, times(1)).create(dayTimetable);
    }

    @Test
    void shouldCallGetDayTimetableByID() {
        when(mockDayTimetable.getById(1)).thenReturn(dayTimetable);
        DayTimetable actualTimetable = dayTimeTableService.getById(1);

        verify(mockDayTimetable, times(1)).getById(1);
        verify(mockGroupService, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetable, actualTimetable);
    }

    @Test
    void shouldCallGetAllTimetables() {
        when(mockDayTimetable.getAll()).thenReturn(dayTimetables);
        List<DayTimetable> actualTimetables = dayTimeTableService.getAll();

        verify(mockDayTimetable, times(1)).getAll();
        verify(mockGroupService, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetables, actualTimetables);
    }

    @Test
    void shouldCallUpdateTimetable() {
        dayTimeTableService.update(1, dayTimetable);

        verify(mockDayTimetable, times(1)).update(1, dayTimetable);
    }

    @Test
    void shouldCallDeleteTimetable() {
        dayTimeTableService.delete(1);

        verify(mockDayTimetable, times(1)).delete(1);
    }

    @Test
    void shouldCallGetDayTimetableByGroupId() {
        when(mockDayTimetable.getByGroupId(1)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByGroupId(1);

        verify(mockDayTimetable, times(1)).getByGroupId(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetableByTeacherId() {
        when(mockDayTimetable.getByTeacherId(1)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByTeacherId(1);

        verify(mockDayTimetable, times(1)).getByTeacherId(1);
        verify(mockGroupService, times(1)).getById(1);
        assertEquals(dayTimetables, actualDayTimetables);
    }

    @Test
    void shouldCallGetDayTimetableByMonthTimetableId() {
        when(mockDayTimetable.getByMonthTimetableId(1)).thenReturn(dayTimetables);
        List<DayTimetable> actualDayTimetables = dayTimeTableService.getByMonthTimetableId(1);

        verify(mockDayTimetable, times(1)).getByMonthTimetableId(1);
        verify(mockGroupService, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getById(1);
        assertEquals(dayTimetables, actualDayTimetables);
    }
}