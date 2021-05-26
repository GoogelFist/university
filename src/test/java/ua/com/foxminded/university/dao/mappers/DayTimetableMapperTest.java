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
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class DayTimetableMapperTest {
    private final DayTimetable expectedDayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, new Group(ID_1_VALUE), new Teacher(ID_2_VALUE), new MonthTimetable(ID_1_VALUE));
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    @Autowired
    private DayTimetableMapper dayTimetableMapper;

    @Test
    void shouldReturnDayTimetableWithCorrectSettings() throws SQLException {
        when(resultSet.getInt(COLUMN_LABEL_ID)).thenReturn(ID_1_VALUE);
        when(resultSet.getObject(COLUMN_START_TIME, LocalTime.class)).thenReturn(DAY_TIMETABLE_1_TIME_VALUE);
        when(resultSet.getString(COLUMN_LABEL_LECTURE_HALL)).thenReturn(LECTURE_HALL_1_VALUE);
        when(resultSet.getString(COLUMN_LABEL_SUBJECT)).thenReturn(SUBJECT_1_VALUE);
        when(resultSet.getInt(COLUMN_LABEL_GROUP_ID)).thenReturn(ID_1_VALUE);
        when(resultSet.getInt(COLUMN_LABEL_TEACHER_ID)).thenReturn(ID_2_VALUE);
        when(resultSet.getInt(COLUMN_LABEL_MONT_TIMETABLE_ID)).thenReturn(ID_1_VALUE);

        DayTimetable actualDayTimetable = dayTimetableMapper.mapRow(resultSet, 1);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }
}