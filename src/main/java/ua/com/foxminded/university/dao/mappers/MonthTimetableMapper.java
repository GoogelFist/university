package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.MonthTimetable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class MonthTimetableMapper implements RowMapper<MonthTimetable> {
    private static final String ID = "id";
    private static final String DATE = "date";

    @Override
    public MonthTimetable mapRow(ResultSet resultSet, int i) throws SQLException {
        MonthTimetable monthTimetable = new MonthTimetable();

        monthTimetable.setId(resultSet.getInt(ID));
        monthTimetable.setDate(resultSet.getObject(DATE, LocalDate.class));

        return monthTimetable;
    }
}
