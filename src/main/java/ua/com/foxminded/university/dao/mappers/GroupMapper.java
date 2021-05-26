package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupMapper implements RowMapper<Group> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CATHEDRA_ID = "cathedra_id";

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group();

        group.setId(resultSet.getInt(ID));
        group.setName(resultSet.getString(NAME));
        group.setCathedra(new Cathedra(resultSet.getInt(CATHEDRA_ID)));

        return group;
    }
}