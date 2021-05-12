package ua.com.foxminded.university.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ua.com.foxminded.university.dao.mappers.MonthTimetableMapper;
import ua.com.foxminded.university.entities.MonthTimetable;

import java.util.List;

import static java.lang.String.format;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.*;

@Component
@PropertySource("classpath:queries.properties")
public class MonthTimetableDAOImpl implements MonthTimetableDAO {
    private static final String MONTH_TIMETABLES = "month_timetables";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String MONTH_TIMETABLE = "month_timetable";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final MonthTimetableMapper monthTimetableMapper;

    private static final Logger logger = LoggerFactory.getLogger(MonthTimetableDAOImpl.class);

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
    public void create(MonthTimetable monthTimetable) throws DaoException {
        logger.debug("create({})", monthTimetable);
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(DATE, monthTimetable.getDate());
            Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
            monthTimetable.setId(generatedID.intValue());
        } catch (DataAccessException e) {
            String message = format(UNABLE_CREATE, monthTimetable);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} created.", monthTimetable);
    }

    @Override
    public MonthTimetable getById(int id) throws DaoException {
        logger.debug("getById {}", id);
        MonthTimetable monthTimetable;
        try {
            monthTimetable = jdbcTemplate.queryForObject(getByID, monthTimetableMapper, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format(ENTITY_NOT_FOUND, MONTH_TIMETABLE, id);
            logger.warn(message);
            throw new DaoException(message, e);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_ID, MONTH_TIMETABLE, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {}", monthTimetable);
        return monthTimetable;
    }

    @Override
    public List<MonthTimetable> getAll() throws DaoException {
        logger.debug("getAll()");
        List<MonthTimetable> monthTimetables;
        try {
            monthTimetables = jdbcTemplate.query(getAll, monthTimetableMapper);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_ALL, MONTH_TIMETABLES);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", monthTimetables.size(), MONTH_TIMETABLES);
        return monthTimetables;
    }

    @Override
    public void update(int id, MonthTimetable monthTimetable) throws DaoException {
        logger.debug("update id {}, {}", id, monthTimetable);
        try {
            jdbcTemplate.update(update, monthTimetable.getDate(), id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE, MONTH_TIMETABLE, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} for id {} was updated", monthTimetable, id);
    }

    @Override
    public void delete(int id) throws DaoException {
        logger.debug("delete Id {}", id);
        try {
            jdbcTemplate.update(delete, id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE, MONTH_TIMETABLE, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} with {} was deleted", MONTH_TIMETABLE, id);
    }
}