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
import ua.com.foxminded.university.dao.mappers.CathedraMapper;
import ua.com.foxminded.university.entities.Cathedra;

import java.util.List;

import static java.lang.String.format;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.*;

@Component
@PropertySource("classpath:queries.properties")
public class CathedraDAOImpl implements CathedraDAO {
    private static final String ID = "id";
    private static final String CATHEDRAS = "cathedras";
    private static final String NAME = "name";
    private static final String CATHEDRA = "cathedra";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final CathedraMapper cathedraMapper;

    private static final Logger logger = LoggerFactory.getLogger(CathedraDAOImpl.class);

    @Value("${cathedras.getByID}")
    private String getByID;

    @Value("${cathedras.getAll}")
    private String getAll;

    @Value("${cathedras.update}")
    private String update;

    @Value("${cathedras.delete}")
    private String delete;

    @Autowired
    public CathedraDAOImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, CathedraMapper cathedraMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(CATHEDRAS).usingGeneratedKeyColumns(ID);
        this.cathedraMapper = cathedraMapper;
    }

    @Override
    public void create(Cathedra cathedra) throws DaoException {
        logger.debug("create({})", cathedra);
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(NAME, cathedra.getName());
            Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
            cathedra.setId(generatedID.intValue());
        } catch (DataAccessException e) {
            String message = format(UNABLE_CREATE, cathedra);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} created.", cathedra);
    }

    @Override
    public Cathedra getById(int id) throws DaoException {
        logger.debug("getById {}", id);
        Cathedra cathedra;
        try {
            cathedra = jdbcTemplate.queryForObject(getByID, cathedraMapper, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format(ENTITY_NOT_FOUND, CATHEDRA, id);
            logger.warn(message);
            throw new DaoException(message, e);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_ID, CATHEDRA, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {}", cathedra);
        return cathedra;
    }

    @Override
    public List<Cathedra> getAll() throws DaoException {
        logger.debug("getAll()");
        List<Cathedra> cathedras;
        try {
            cathedras = jdbcTemplate.query(getAll, cathedraMapper);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_ALL, CATHEDRAS);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", cathedras.size(), CATHEDRAS);
        return cathedras;
    }

    @Override
    public void update(int id, Cathedra cathedra) throws DaoException {
        logger.debug("update id {}, {}", id, cathedra);
        try {
            jdbcTemplate.update(update, cathedra.getName(), id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE, CATHEDRA, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} for id {} was updated", cathedra, id);
    }

    @Override
    public void delete(int id) throws DaoException {
        logger.debug("delete Id {}", id);
        try {
            jdbcTemplate.update(delete, id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE, CATHEDRA, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} with {} was deleted", CATHEDRA, id);
    }
}