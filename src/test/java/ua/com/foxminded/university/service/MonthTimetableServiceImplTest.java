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
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.repository.MonthTimetableRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MonthTimetableServiceImplTest {
    @Mock
    private MonthTimetableRepository mockMonthTimetableRepository;

    private MonthTimetableService monthTimetableService;
    private MonthTimetable monthTimetable;
    private List<MonthTimetable> monthTimetables;

    @BeforeEach
    void setUp() {
        monthTimetableService = new MonthTimetableServiceImpl(mockMonthTimetableRepository);
        monthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1);
        monthTimetables = singletonList(monthTimetable);
    }

    @Test
    void shouldCallCreateMonthTimetable() {
        monthTimetableService.create(monthTimetable);

        verify(mockMonthTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(monthTimetable);
    }

    @Test
    void shouldCallGetByMonthTimetableId() {
        when(mockMonthTimetableRepository.findById(ID_1_VALUE)).thenReturn(java.util.Optional.ofNullable(monthTimetable));
        MonthTimetable actualMonthTimetable = monthTimetableService.getById(ID_1_VALUE);

        verify(mockMonthTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(monthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldCallGetAllMonthTimetables() {
        when(mockMonthTimetableRepository.findAll()).thenReturn(monthTimetables);
        List<MonthTimetable> actualMonthTimetables = monthTimetableService.getAll();

        verify(mockMonthTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(monthTimetables, actualMonthTimetables);
    }

    @Test
    void shouldCallGetAllMonthTimetablesPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        monthTimetables = new ArrayList<>();
        monthTimetables.add(new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1));
        Page<MonthTimetable> expectedPageDayTimetable = new PageImpl<>(monthTimetables, pageable, monthTimetables.size());

        when(mockMonthTimetableRepository.findAll(pageable)).thenReturn(expectedPageDayTimetable);

        Page<MonthTimetable> actualPageDayTimetable = monthTimetableService.getAll(pageable);

        verify(mockMonthTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedPageDayTimetable, actualPageDayTimetable);
    }

    @Test
    void shouldCallUpdateMonthTimetable() {
        monthTimetableService.update(monthTimetable);

        verify(mockMonthTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(monthTimetable);
    }

    @Test
    void shouldCallDeleteMonthTimetable() {
        monthTimetableService.delete(ID_1_VALUE);

        verify(mockMonthTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetById() {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, GET, MONTH_TIMETABLE, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockMonthTimetableRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockMonthTimetableRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}