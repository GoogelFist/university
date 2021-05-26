package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {

    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String GROUP_ID = "group_id";

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getInt(ID));
        student.setFirstName(resultSet.getString(FIRST_NAME));
        student.setLastName(resultSet.getString(LAST_NAME));
        student.setPhone(resultSet.getString(PHONE));
        student.setGroup(new Group(resultSet.getInt(GROUP_ID)));

        return student;
    }
}