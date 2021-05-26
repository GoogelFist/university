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
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.entities.Group;

import java.util.List;

import static java.lang.String.format;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.*;

@Component
@PropertySource("classpath:queries.properties")
public class GroupDAOImpl implements GroupDAO {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String GROUPS = "groups";
    private static final String GROUP = "group";
    private static final String CATHEDRA_ID = "cathedra_id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final GroupMapper groupMapper;

    private static final Logger logger = LoggerFactory.getLogger(GroupDAOImpl.class);

    @Value("${groups.getByID}")
    private String getByID;

    @Value("${groups.getAll}")
    private String getAll;

    @Value("${groups.update}")
    private String update;

    @Value("${groups.delete}")
    private String delete;

    @Value("${groups.getByCathedraId}")
    private String getByCathedraId;

    @Value("${groups.assignToCathedra}")
    private String assignToCathedra;

    @Value("${groups.updateAssignment}")
    private String updateAssignment;

    @Value("${groups.deleteAssignment}")
    private String deleteAssignment;

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, GroupMapper groupMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(GROUPS).usingGeneratedKeyColumns(ID);
        this.groupMapper = groupMapper;
    }

    @Override
    public void create(Group group) throws DaoException {
        logger.debug("create({})", group);
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(NAME, group.getName())
                .addValue(CATHEDRA_ID, group.getCathedra().getId());
            Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
            group.setId(generatedID.intValue());
        } catch (DataAccessException e) {
            String message = format(UNABLE_CREATE, group);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} created.", group);
    }

    @Override
    public Group getById(int id) throws DaoException {
        logger.debug("getById {}", id);
        Group group;
        try {
            group = jdbcTemplate.queryForObject(getByID, groupMapper, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format(ENTITY_NOT_FOUND, GROUP, id);
            logger.warn(message);
            throw new DaoException(message, e);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_ID, GROUP, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {}", group);
        return group;
    }

    @Override
    public List<Group> getAll() throws DaoException {
        logger.debug("getAll {]");
        List<Group> groups;
        try {
            groups = jdbcTemplate.query(getAll, groupMapper);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_ALL, GROUPS);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", groups.size(), GROUPS);
        return groups;
    }

    @Override
    public void update(int id, Group group) throws DaoException {
        logger.debug("update id {}, {}", id, group);
        try {
            jdbcTemplate.update(update, group.getName(), id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE, GROUP, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} for id {} was updated", group, id);
    }

    @Override
    public void delete(int id) throws DaoException {
        logger.debug("delete Id {}", id);
        try {
            jdbcTemplate.update(delete, id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE, GROUP, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} with {} was deleted", GROUP, id);
    }

    @Override
    public List<Group> getByCathedraId(int cathedraId) throws DaoException {
        logger.debug("getByCathedraId {}", cathedraId);
        List<Group> groups;
        try {
            groups = jdbcTemplate.query(getByCathedraId, groupMapper, cathedraId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_CATHEDRA_ID, GROUPS, cathedraId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", groups.size(), GROUPS);
        return groups;
    }

    @Override
    public void assignToCathedra(int cathedraId, int groupId) throws DaoException {
        logger.debug("assignToCathedra(cathedraId {}, groupId {})", cathedraId, groupId);
        try {
            jdbcTemplate.update(assignToCathedra, cathedraId, groupId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_ASSIGN_GROUP_TO_CATHEDRA, groupId, cathedraId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Group with id {} was assigned to cathedra with cathedraId {}", groupId, cathedraId);
    }

    @Override
    public void updateAssignment(int cathedraId, int groupId) throws DaoException {
        logger.debug("updateAssignment(cathedraId {}, groupId {})", cathedraId, groupId);
        try {
            jdbcTemplate.update(updateAssignment, cathedraId, groupId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE_ASSIGNMENT_GROUP_TO_CATHEDRA, groupId, cathedraId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Assignment group with id {} to cathedra with id {} was updated", groupId, cathedraId);
    }

    @Override
    public void deleteAssignment(int groupId) throws DaoException {
        logger.debug("deleteAssignment(groupId {})", groupId);
        try {
            jdbcTemplate.update(deleteAssignment, groupId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE_ASSIGNMENT_GROUP, groupId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Group with id {} was deleted from cathedra", groupId);
    }
}