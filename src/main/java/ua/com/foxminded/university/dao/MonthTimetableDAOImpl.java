package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mappers.MonthTimetableMapper;
import ua.com.foxminded.university.entities.MonthTimetable;

import java.util.List;

@Component
@PropertySource("classpath:queries.properties")
public class MonthTimetableDAOImpl implements MonthTimetableDAO {
    private static final String MONTH_TIMETABLES = "month_timetables";
    private static final String ID = "id";
    private static final String DATE = "date";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final MonthTimetableMapper monthTimetableMapper;

    @Value("${month_timetables.getByID}")
    private String getByID;

    @Value("${month_timetables.getAll}")
    private String getAll;

    @Value("${month_timetables.update}")
    private String update;

    @Value("${month_timetables.delete}")
    private String delete;

    public MonthTimetableDAOImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, MonthTimetableMapper monthTimetableMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(MONTH_TIMETABLES).usingGeneratedKeyColumns(ID);
        this.monthTimetableMapper = monthTimetableMapper;
    }

    @Override
    public void create(MonthTimetable monthTimetable) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue(DATE, monthTimetable.getDate());
        Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
        monthTimetable.setId(generatedID.intValue());
    }

    @Override
    public MonthTimetable getByID(int id) {
        return jdbcTemplate.queryForObject(getByID, monthTimetableMapper, id);
    }

    @Override
    public List<MonthTimetable> getAll() {
        return jdbcTemplate.query(getAll, monthTimetableMapper);
    }

    @Override
    public void update(int id, MonthTimetable monthTimetable) {
        jdbcTemplate.update(update, monthTimetable.getDate(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(delete, id);
    }
}