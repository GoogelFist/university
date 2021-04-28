package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.entities.Group;

import java.util.List;

@Component
@PropertySource("classpath:queries.properties")
public class GroupDAOImpl implements GroupDAO {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String GROUPS = "groups";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final GroupMapper groupMapper;

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
    public void create(Group group) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue(NAME, group.getName());
        Number generatedID = jdbcInsert.executeAndReturnKey(parameterSource);
        group.setId(generatedID.intValue());
    }

    @Override
    public Group getById(int id) {
        return jdbcTemplate.queryForObject(getByID, groupMapper, id);
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(getAll, groupMapper);
    }

    @Override
    public void update(int id, Group group) {
        jdbcTemplate.update(update, group.getName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(delete, id);
    }

    @Override
    public List<Group> getByCathedraId(int cathedraId) {
        return jdbcTemplate.query(getByCathedraId, groupMapper, cathedraId);
    }

    @Override
    public void assignToCathedra(int cathedraId, int groupId) {
        jdbcTemplate.update(assignToCathedra, cathedraId, groupId);
    }

    @Override
    public void updateAssignment(int cathedraId, int groupId) {
        jdbcTemplate.update(updateAssignment, cathedraId, groupId);
    }

    @Override
    public void deleteAssignment(int groupId) {
        jdbcTemplate.update(deleteAssignment, groupId);
    }
}