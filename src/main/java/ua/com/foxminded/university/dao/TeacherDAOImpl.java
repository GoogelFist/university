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
import ua.com.foxminded.university.dao.mappers.TeacherMapper;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

import static java.lang.String.format;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.*;

@Component
@PropertySource("classpath:queries.properties")
public class TeacherDAOImpl implements TeacherDAO {

    private static final String TEACHERS = "teachers";
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String QUALIFICATION = "qualification";
    private static final String TEACHER = "teacher";
    private static final String CATHEDRA_ID = "cathedra_id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final TeacherMapper teacherMapper;

    private static final Logger logger = LoggerFactory.getLogger(TeacherDAOImpl.class);

    @Value("${teachers.getByID}")
    private String getByID;

    @Value("${teachers.getAll}")
    private String getAll;

    @Value("${teachers.update}")
    private String update;

    @Value("${teachers.delete}")
    private String delete;

    @Value("${teachers.getByCathedraId}")
    private String getByCathedraId;

    @Value("${teachers.assignToCathedra}")
    private String assignToCathedra;

    @Value("${teachers.updateAssignment}")
    private String updateAssignment;

    @Value("${teachers.deleteAssignment}")
    private String deleteAssignment;

    @Autowired
    public TeacherDAOImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, TeacherMapper teacherMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(TEACHERS).usingGeneratedKeyColumns(ID);
        this.teacherMapper = teacherMapper;
    }

    @Override
    public void create(Teacher teacher) throws DaoException {
        logger.debug("create({})", teacher);
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(FIRST_NAME, teacher.getFirstName())
                .addValue(LAST_NAME, teacher.getLastName())
                .addValue(PHONE, teacher.getPhone())
                .addValue(QUALIFICATION, teacher.getQualification())
                .addValue(CATHEDRA_ID, teacher.getCathedra().getId());
            Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
            teacher.setId(generatedID.intValue());
        } catch (DataAccessException e) {
            String message = format(UNABLE_CREATE, teacher);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} created", teacher);
    }

    @Override
    public Teacher getById(int id) throws DaoException {
        logger.debug("getById {}", id);
        Teacher teacher;
        try {
            teacher = jdbcTemplate.queryForObject(getByID, teacherMapper, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format(ENTITY_NOT_FOUND, TEACHER, id);
            logger.warn(message);
            throw new DaoException(message, e);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_ID, TEACHER, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {}", teacher);
        return teacher;
    }

    @Override
    public List<Teacher> getAll() throws DaoException {
        logger.debug("getAll()");
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(getAll, teacherMapper);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_ALL, TEACHER);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", teachers.size(), TEACHERS);
        return teachers;
    }

    @Override
    public void update(int id, Teacher teacher) throws DaoException {
        logger.debug("update id {}, {}", id, teacher);
        try {
            jdbcTemplate.update(update, teacher.getFirstName(), teacher.getLastName(), teacher.getPhone(), teacher.getQualification(), id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE, TEACHER, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} for id {} was updated", teacher, id);
    }

    @Override
    public void delete(int id) throws DaoException {
        logger.debug("delete Id {}", id);
        try {
            jdbcTemplate.update(delete, id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE, TEACHER, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} with {} was deleted", TEACHER, id);
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) throws DaoException {
        logger.debug("getByCathedraId {}", cathedraId);
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(getByCathedraId, teacherMapper, cathedraId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_CATHEDRA_ID, TEACHERS, cathedraId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", teachers.size(), TEACHERS);
        return teachers;
    }

    @Override
    public void assignToCathedra(int cathedraId, int teacherId) throws DaoException {
        logger.debug("assignToCathedra(cathedraId {}, teacherId {})", cathedraId, teacherId);
        try {
            jdbcTemplate.update(assignToCathedra, cathedraId, teacherId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_ASSIGN_TEACHER_TO_CATHEDRA, teacherId, cathedraId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Teacher with id {} was assigned to cathedra with cathedraId {}", teacherId, cathedraId);
    }

    @Override
    public void updateAssignment(int cathedraId, int teacherId) throws DaoException {
        logger.debug("updateAssignment(cathedraId {}, teacherId {})", cathedraId, teacherId);
        try {
            jdbcTemplate.update(updateAssignment, cathedraId, teacherId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE_ASSIGNMENT_TEACHER_TO_CATHEDRA, teacherId, cathedraId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Assignment teacher with id {} to cathedra with id {} was updated", teacherId, cathedraId);
    }

    @Override
    public void deleteAssignment(int teacherId) throws DaoException {
        logger.debug("deleteAssignment(teacherId {})", teacherId);
        try {
            jdbcTemplate.update(deleteAssignment, teacherId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE_ASSIGNMENT_TEACHER, teacherId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Teacher with id {} was deleted from cathedra", teacherId);
    }
}