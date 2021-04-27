package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

@Component
public class DayTimetableMapper implements RowMapper<DayTimetable> {
    private static final String ID = "id";
    private static final String START_TIME = "start_time";
    private static final String LECTURE_HALL = "lecture_hall";
    private static final String SUBJECT = "subject";
    private static final String GROUP_ID = "group_id";
    private static final String TEACHER_ID = "teacher_id";

    @Override
    public DayTimetable mapRow(ResultSet resultSet, int i) throws SQLException {
        DayTimetable dayTimetable = new DayTimetable();

        dayTimetable.setId(resultSet.getInt(ID));
        dayTimetable.setStartTime(resultSet.getObject(START_TIME, LocalTime.class));
        dayTimetable.setLectureHall(resultSet.getString(LECTURE_HALL));
        dayTimetable.setSubject(resultSet.getString(SUBJECT));
        dayTimetable.setGroup(new Group(resultSet.getInt(GROUP_ID)));
        dayTimetable.setTeacher(new Teacher(resultSet.getInt(TEACHER_ID)));

        return dayTimetable;
    }
}