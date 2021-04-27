package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

@Component
@PropertySource("classpath:queries.properties")
public class StudentDAOImpl implements StudentDAO {

    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String STUDENTS = "students";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final StudentMapper studentMapper;

    @Value("${students.getByID}")
    private String getByID;

    @Value("${students.getAll}")
    private String getAll;

    @Value("${students.update}")
    private String update;

    @Value("${students.delete}")
    private String delete;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = jdbcInsert.withTableName(STUDENTS).usingGeneratedKeyColumns(ID);
        this.studentMapper = studentMapper;
    }

    @Override
    public void create(Student student) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue(FIRST_NAME, student.getFirstName())
            .addValue(LAST_NAME, student.getLastName())
            .addValue(PHONE, student.getPhone());
        Number generatedId = jdbcInsert.executeAndReturnKey(parameterSource);
        student.setId(generatedId.intValue());
    }

    @Override
    public Student getByID(int id) {
        return jdbcTemplate.queryForObject(getByID, studentMapper, id);
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(getAll, studentMapper);
    }

    @Override
    public void update(int id, Student student) {
        jdbcTemplate.update(update, student.getFirstName(), student.getLastName(), student.getPhone(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(delete, id);
    }
}