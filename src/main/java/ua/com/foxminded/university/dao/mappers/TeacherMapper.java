package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TeacherMapper implements RowMapper<Teacher> {
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String QUALIFICATION = "qualification";
    private static final String CATHEDRA_ID = "cathedra_id";

    @Override
    public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {
        Teacher teacher = new Teacher();

        teacher.setId(resultSet.getInt(ID));
        teacher.setFirstName(resultSet.getString(FIRST_NAME));
        teacher.setLastName(resultSet.getString(LAST_NAME));
        teacher.setPhone(resultSet.getString(PHONE));
        teacher.setQualification(resultSet.getString(QUALIFICATION));
        teacher.setCathedra(new Cathedra(resultSet.getInt(CATHEDRA_ID)));

        return teacher;
    }
}