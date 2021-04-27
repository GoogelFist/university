package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Cathedra;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CathedraMapper implements RowMapper<Cathedra> {
    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public Cathedra mapRow(ResultSet resultSet, int i) throws SQLException {
        Cathedra cathedra = new Cathedra();

        cathedra.setId(resultSet.getInt(ID));
        cathedra.setName(resultSet.getString(NAME));

        return cathedra;
    }
}