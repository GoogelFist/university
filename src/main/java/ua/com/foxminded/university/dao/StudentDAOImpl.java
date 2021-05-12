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
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

import static java.lang.String.format;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.*;

@Repository
@PropertySource("classpath:queries.properties")
public class StudentDAOImpl implements StudentDAO {

    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String STUDENTS = "students";
    private static final String STUDENT = "student";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final StudentMapper studentMapper;

    private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);

    @Value("${students.getByID}")
    private String getByID;

    @Value("${students.getAll}")
    private String getAll;

    @Value("${students.update}")
    private String update;

    @Value("${students.delete}")
    private String delete;

    @Value("${students.getByGroupId}")
    private String getByGroupId;

    @Value("${students.assignToGroup}")
    private String assignToGroup;

    @Value("${students.updateAssignment}")
    private String updateAssignment;

    @Value("${students.deleteAssignment}")
    private String deleteAssignment;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(STUDENTS).usingGeneratedKeyColumns(ID);
        this.studentMapper = studentMapper;
    }

    @Override
    public void create(Student student) throws DaoException {
        logger.debug("create({})", student);
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(FIRST_NAME, student.getFirstName())
                .addValue(LAST_NAME, student.getLastName())
                .addValue(PHONE, student.getPhone());
            Number generatedId = jdbcInsert.executeAndReturnKey(parameterSource);
            student.setId(generatedId.intValue());
        } catch (DataAccessException e) {
            String message = format(UNABLE_CREATE, student);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} created.", student);
    }

    @Override
    public Student getById(int id) throws DaoException {
        logger.debug("getById {}", id);
        Student student;
        try {
            student = jdbcTemplate.queryForObject(getByID, studentMapper, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format(ENTITY_NOT_FOUND, STUDENT, id);
            logger.warn(message);
            throw new DaoException(message, e);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_ID, STUDENT, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {}", student);
        return student;
    }

    @Override
    public List<Student> getAll() throws DaoException {
        logger.debug("getAll()");
        List<Student> students;
        try {
            students = jdbcTemplate.query(getAll, studentMapper);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_ALL, STUDENTS);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", students.size(), STUDENTS);
        return students;
    }

    @Override
    public void update(int id, Student student) throws DaoException {
        logger.debug("update id {}, {}", id, student);
        try {
            jdbcTemplate.update(this.update, student.getFirstName(), student.getLastName(), student.getPhone(), id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE, STUDENT, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} for id {} was updated", student, id);
    }

    @Override
    public void delete(int id) throws DaoException {
        logger.debug("delete Id {}", id);
        try {
            jdbcTemplate.update(delete, id);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE, STUDENT, id);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("{} with {} was deleted", STUDENT, id);
    }

    @Override
    public List<Student> getByGroupId(int groupId) throws DaoException {
        logger.debug("getByGroupId {}", groupId);
        List<Student> students;
        try {
            students = jdbcTemplate.query(getByGroupId, studentMapper, groupId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_GET_BY_GROUP_ID, STUDENTS, groupId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Found {} {}", students.size(), STUDENTS);
        return students;
    }

    @Override
    public void assignToGroup(int groupId, int studentId) throws DaoException {
        logger.debug("assignToGroup(groupId {}, studentId {})", groupId, studentId);
        try {
            jdbcTemplate.update(assignToGroup, groupId, studentId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_ASSIGN_STUDENT_TO_GROUP, studentId, groupId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Student with id {} was assigned to group with groupId {}", studentId, groupId);
    }

    @Override
    public void updateAssignment(int groupId, int studentId) throws DaoException {
        logger.debug("updateAssignment(groupId {}, studentId {})", groupId, studentId);
        try {
            jdbcTemplate.update(updateAssignment, groupId, studentId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_UPDATE_ASSIGNMENT_STUDENT_TO_GROUP, studentId, groupId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Assignment student with id {} to group with id {} was updated", studentId, groupId);
    }

    @Override
    public void deleteAssignment(int studentId) throws DaoException {
        logger.debug("deleteAssignment(studentId {})", studentId);
        try {
            jdbcTemplate.update(deleteAssignment, studentId);
        } catch (DataAccessException e) {
            String message = format(UNABLE_DELETE_ASSIGNMENT_STUDENT, studentId);
            logger.error(message);
            throw new DaoException(message, e);
        }
        logger.debug("Student with id {} was deleted from group", studentId);
    }
}