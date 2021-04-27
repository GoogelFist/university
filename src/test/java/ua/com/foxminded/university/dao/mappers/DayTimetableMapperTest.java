package ua.com.foxminded.university.dao.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DaoTestConfig;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class DayTimetableMapperTest {
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    @Autowired
    private DayTimetableMapper dayTimetableMapper;

    private final DayTimetable expectedDayTimetable = new DayTimetable(1, LocalTime.of(8, 0), "12", "math", new Group(1), new Teacher(2));

    @Test
    void shouldReturnDayTimetableWithCorrectSettings() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getObject("start_time", LocalTime.class)).thenReturn(LocalTime.of(8, 0));
        when(resultSet.getString("lecture_hall")).thenReturn("12");
        when(resultSet.getString("subject")).thenReturn("math");
        when(resultSet.getInt("group_id")).thenReturn(1);
        when(resultSet.getInt("teacher_id")).thenReturn(2);

        DayTimetable actualDayTimetable = dayTimetableMapper.mapRow(resultSet, 1);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }
}