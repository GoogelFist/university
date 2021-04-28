package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mappers.CathedraMapper;
import ua.com.foxminded.university.entities.Cathedra;

import java.util.List;

@Component
@PropertySource("classpath:queries.properties")
public class CathedraDAOImpl implements CathedraDAO {
    private static final String ID = "id";
    private static final String CATHEDRAS = "cathedras";
    private static final String NAME = "name";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final CathedraMapper cathedraMapper;

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
    public void create(Cathedra cathedra) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue(NAME, cathedra.getName());
        Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
        cathedra.setId(generatedID.intValue());
    }

    @Override
    public Cathedra getById(int id) {
        return jdbcTemplate.queryForObject(getByID, cathedraMapper, id);
    }

    @Override
    public List<Cathedra> getAll() {
        return jdbcTemplate.query(getAll, cathedraMapper);
    }

    @Override
    public void update(int id, Cathedra cathedra) {
        jdbcTemplate.update(update, cathedra.getName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(delete, id);
    }
}