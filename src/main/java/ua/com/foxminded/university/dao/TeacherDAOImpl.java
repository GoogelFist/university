package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mappers.TeacherMapper;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

@Component
@PropertySource("classpath:queries.properties")
public class TeacherDAOImpl implements TeacherDAO {

    private static final String TEACHERS = "teachers";
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String QUALIFICATION = "qualification";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final TeacherMapper teacherMapper;

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
    public void create(Teacher teacher) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue(FIRST_NAME, teacher.getFirstName())
            .addValue(LAST_NAME, teacher.getLastName())
            .addValue(PHONE, teacher.getPhone())
            .addValue(QUALIFICATION, teacher.getQualification());
        Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
        teacher.setId(generatedID.intValue());
    }

    @Override
    public Teacher getById(int id) {
        return jdbcTemplate.queryForObject(getByID, teacherMapper, id);
    }

    @Override
    public List<Teacher> getAll() {
        return jdbcTemplate.query(getAll, teacherMapper);
    }

    @Override
    public void update(int id, Teacher teacher) {
        jdbcTemplate.update(update, teacher.getFirstName(), teacher.getLastName(), teacher.getPhone(), teacher.getQualification(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(delete, id);
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) {
        return jdbcTemplate.query(getByCathedraId, teacherMapper, cathedraId);
    }

    @Override
    public void assignToCathedra(int cathedraId, int teacherId) {
        jdbcTemplate.update(assignToCathedra, cathedraId, teacherId);
    }

    @Override
    public void updateAssignment(int cathedraId, int teacherId) {
        jdbcTemplate.update(updateAssignment, cathedraId, teacherId);
    }

    @Override
    public void deleteAssignment(int teacherId) {
        jdbcTemplate.update(deleteAssignment, teacherId);
    }
}