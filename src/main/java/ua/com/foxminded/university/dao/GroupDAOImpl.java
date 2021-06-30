package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.Group;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class GroupDAOImpl implements GroupDAO {
    private static final String GROUPS = "groups";
    private static final String CATHEDRA_ID = "cathedraId";
    private static final String QUERY_GET_ALL = "from Group order by id";
    private static final String QUERY_GET_BY_GROUP_ID = "from Group group where group.cathedra.id = :cathedraId order by id";

    private static final String CREATE = "create({})";
    private static final String CREATED = "{} created";
    private static final String GET_BY_ID = "getById({})";
    private static final String FOUND_BY_ID = "Found {}";
    private static final String GET_ALL = "getAll()";
    private static final String FOUND_ALL = "Found {} {}";
    private static final String UPDATE = "update({})";
    private static final String UPDATED = "{} was updated";
    private static final String DELETE = "delete(Id {})";
    private static final String DELETED = "{} was deleted";
    private static final String GET_BY_CATHEDRA_ID = "getByCathedraId {}";
    private static final String FOUND_BY_CATHEDRA_ID = "Found {} {}";


    private final EntityManager entityManager;

    @Autowired
    public GroupDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Group group) {
        log.debug(CREATE, group);

        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(group);

        log.debug(CREATED, group);
    }

    @Override
    public Group getById(int id) {
        log.debug(GET_BY_ID, id);

        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, id);

        log.debug(FOUND_BY_ID, group);
        return group;
    }

    @Override
    public List<Group> getAll() {
        log.debug(GET_ALL);

        Session session = entityManager.unwrap(Session.class);
        @SuppressWarnings("unchecked")
        List<Group> groups = session.createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, groups.size(), GROUPS);
        return groups;
    }

    @Override
    public void update(Group group) {
        log.debug(UPDATE, group);

        Session session = entityManager.unwrap(Session.class);
        session.update(group);

        log.debug(UPDATED, group);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, id);

        if (group != null) {
            session.delete(group);
            log.debug(DELETED, group);
        }
    }

    @Override
    public List<Group> getByCathedraId(int cathedraId) {
        log.debug(GET_BY_CATHEDRA_ID, cathedraId);

        Session session = entityManager.unwrap(Session.class);
        @SuppressWarnings("unchecked")
        List<Group> groups = session.createQuery(QUERY_GET_BY_GROUP_ID).setParameter(CATHEDRA_ID, cathedraId).list();

        log.debug(FOUND_BY_CATHEDRA_ID, groups.size(), GROUPS);
        return groups;
    }
}