package ua.com.foxminded.university.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.dao.mappers.DayTimetableMapper;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

import static java.lang.String.format;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.*;

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
    private static final String DAY_TIMETABLE = "day_timetable";
    private static final String FOUND_DAY_TIMETABLES = "Found {} {}";
    private static final String MONTH_TIMETABLE_ID = "month_timetable_id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final DayTimetableMapper dayTimetableMapper;

    private static final Logger logger = LoggerFactory.getLogger(DayTimetableDAOIml.class);

    @Value("${day_timetables.getByID}")
    private String getByID;

    @Value("${day_timetables.getAll}")
    private String getAll;

    @Value("${day_timetables.update}")
    private String update;

    @Value("${day_timetables.delete}")
    private String delete;

    @Value("${day_timetables.getByGroupId}")
    private String getByGroupId;

    @Value("${day_timetables.getByTeacherId}")
    private String getByTeacherId;

    @Value("${day_timetables.getByMonthTimetableId}")
    private String getByMonthTimetableId;

    @Autowired
    public DayTimetableDAOIml(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, DayTimetableMapper dayTimetableMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(DAY_TIMETABLES).usingGeneratedKeyColumns(ID);
        this.dayTimetableMapper = dayTimetableMapper;
    }

    @Override
    public void create(DayTimetable dayTimetable) throws DaoException {
        logger.debug("create({})", dayTimetable);
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(START_TIME, dayTimetable.getStartTime())
                .addValue(LECTURE_HALL, dayTimetable.getLectureHall())
                .addValue(SUBJECT, dayTimetable.getSubject())
                .addValue(GROUP_ID, dayTimetable.getGroup().getId())
                .addValue(TEACHER_ID, dayTimetable.getTeacher().getId())
                .addValue(MONTH_TIMETABLE_ID, dayTimetable.getMonthTimetable().getId());
            Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
            dayTimetable.setId(generatedID.intValue());
        } catch (DataAccessException e) {
            String message = format(UNABLE_CREATE, dayTimetable);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} created.", dayTimetable);
    }

    @Override
    public DayTimetable getById(int id) throws DaoException {
        logger.debug("getById {}", id);
        DayTimetable dayTimetable;
        try {
            dayTimetable = jdbcTemplate.queryForObject(getByID, dayTimetableMapper, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format(ENTITY_NOT_FOUND, DAY_TIMETABLE, id);
            logger.warn(message);
            throw new DaoException(message, e);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_ID, DAY_TIMETABLE, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {}", dayTimetable);
        return dayTimetable;
    }

    @Override
    public List<DayTimetable> getAll() throws DaoException {
        logger.debug("getAll()");
        List<DayTimetable> dayTimetables;
        try {
            dayTimetables = jdbcTemplate.query(getAll, dayTimetableMapper);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_ALL, DAY_TIMETABLES);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug(FOUND_DAY_TIMETABLES, dayTimetables.size(), DAY_TIMETABLES);
        return dayTimetables;
    }

    @Override
    public void update(int id, DayTimetable dayTimetable) throws DaoException {
        logger.debug("update id {}, {}", id, dayTimetable);
        try {
            jdbcTemplate.update(update, dayTimetable.getStartTime(), dayTimetable.getLectureHall(),
                dayTimetable.getSubject(), dayTimetable.getGroup().getId(), dayTimetable.getTeacher().getId(), id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE, DAY_TIMETABLE, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} for id {} was updated", dayTimetable, id);
    }

    @Override
    public void delete(int id) throws DaoException {
        logger.debug("delete Id {}", id);
        try {
            jdbcTemplate.update(delete, id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE, DAY_TIMETABLE, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} with {} was deleted", DAY_TIMETABLE, id);
    }

    @Override
    public List<DayTimetable> getByGroupId(int groupId) throws DaoException {
        logger.debug("getByGroupId {}", groupId);
        List<DayTimetable> dayTimetables;
        try {
            dayTimetables = jdbcTemplate.query(getByGroupId, dayTimetableMapper, groupId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_GROUP_ID, DAY_TIMETABLES, groupId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug(FOUND_DAY_TIMETABLES, dayTimetables.size(), DAY_TIMETABLES);
        return dayTimetables;
    }

    @Override
    public List<DayTimetable> getByTeacherId(int teacherId) throws DaoException {
        logger.debug("getByTeacherId {}", teacherId);
        List<DayTimetable> dayTimetables;
        try {
            dayTimetables = jdbcTemplate.query(getByTeacherId, dayTimetableMapper, teacherId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_TEACHER_ID, DAY_TIMETABLES, teacherId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug(FOUND_DAY_TIMETABLES, dayTimetables.size(), DAY_TIMETABLES);
        return dayTimetables;
    }

    @Override
    public List<DayTimetable> getByMonthTimetableId(int id) throws DaoException {
        logger.debug("getByTeacherId {}", id);
        List<DayTimetable> dayTimetables;
        try {
            dayTimetables = jdbcTemplate.query(getByMonthTimetableId, dayTimetableMapper, id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_MONTH_TIMETABLE_ID, DAY_TIMETABLES, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug(FOUND_DAY_TIMETABLES, dayTimetables.size(), DAY_TIMETABLES);
        return dayTimetables;
    }
}