package ua.com.foxminded.university.dao.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DaoTestConfig;
import ua.com.foxminded.university.entities.MonthTimetable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class MonthTimetableMapperTest {
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    @Autowired
    private MonthTimetableMapper monthTimetableMapper;

    private final MonthTimetable expectedMonthTimetable = new MonthTimetable(1, LocalDate.of(2021, 4, 24));

    @Test
    void shouldReturnMonthTimetableWithCorrectSettings() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getObject("date", LocalDate.class)).thenReturn(LocalDate.of(2021, 4, 24));

        MonthTimetable actualMonthTimetable = monthTimetableMapper.mapRow(resultSet, 1);

        assertEquals(expectedMonthTimetable, actualMonthTimetable);
    }
}