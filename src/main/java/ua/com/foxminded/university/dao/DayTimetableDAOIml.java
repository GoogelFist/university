package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mappers.DayTimetableMapper;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

@Component
@PropertySource("classpath:queries.properties")
public class DayTimetableDAOIml implements DayTimetableDAO {

    private static final String DAY_TIMETABLES = "day_timetables";
    private static final String ID = "id";
    private static final String START_TIME = "start_time";
    private static final String LECTURE_HALL = "lecture_hall";
    private static final String SUBJECT = "subject";
    private static final String GROUP_ID = "group_id";
    private static final String TEACHER_ID = "teacher_id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final DayTimetableMapper dayTimetableMapper;

    @Value("${day_timetables.getByID}")
    private String getByID;

    @Value("${day_timetables.getAll}")
    private String getAll;

    @Value("${day_timetables.update}")
    private String update;

    @Value("${day_timetables.delete}")
    private String delete;

    @Autowired
    public DayTimetableDAOIml(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, DayTimetableMapper dayTimetableMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(DAY_TIMETABLES).usingGeneratedKeyColumns(ID);
        this.dayTimetableMapper = dayTimetableMapper;
    }

    @Override
    public void create(DayTimetable dayTimetable) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue(START_TIME, dayTimetable.getStartTime())
            .addValue(LECTURE_HALL, dayTimetable.getLectureHall())
            .addValue(SUBJECT, dayTimetable.getSubject())
            .addValue(GROUP_ID, dayTimetable.getGroup().getId())
            .addValue(TEACHER_ID, dayTimetable.getTeacher().getId());
        Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
        dayTimetable.setId(generatedID.intValue());
    }

    @Override
    public DayTimetable getByID(int id) {
        return jdbcTemplate.queryForObject(getByID, dayTimetableMapper, id);
    }

    @Override
    public List<DayTimetable> getAll() {
        return jdbcTemplate.query(getAll, dayTimetableMapper);
    }

    @Override
    public void update(int id, DayTimetable dayTimetable) {
        jdbcTemplate.update(update, dayTimetable.getStartTime(), dayTimetable.getLectureHall(),
            dayTimetable.getSubject(), dayTimetable.getGroup().getId(), dayTimetable.getTeacher().getId(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(delete, id);
    }
}