package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.Teacher;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class TeacherDAOImpl implements TeacherDAO {
    private static final String TEACHERS = "teachers";
    private static final String CATHEDRA_ID = "cathedraId";
    private static final String QUERY_GET_ALL = "from Teacher order by id";
    private static final String QUERY_GET_BY_CATHEDRA_ID = "from Teacher teacher where teacher.cathedra.id = :cathedraId order by id";

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
    public TeacherDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Teacher teacher) {
        log.debug(CREATE, teacher);

        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(teacher);

        log.debug(CREATED, teacher);
    }

    @Override
    public Teacher getById(int id) {
        log.debug(GET_BY_ID, id);

        Session session = entityManager.unwrap(Session.class);
        Teacher teacher = session.get(Teacher.class, id);

        log.debug(FOUND_BY_ID, teacher);
        return teacher;
    }

    @Override
    public List<Teacher> getAll() {
        log.debug(GET_ALL);

        Session session = entityManager.unwrap(Session.class);
        @SuppressWarnings("unchecked")
        List<Teacher> teachers = session.createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, teachers.size(), TEACHERS);
        return teachers;
    }

    @Override
    public void update(Teacher teacher) {
        log.debug(UPDATE, teacher);

        Session session = entityManager.unwrap(Session.class);
        session.update(teacher);

        log.debug(UPDATED, teacher);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = entityManager.unwrap(Session.class);
        Teacher teacher = session.get(Teacher.class, id);
        session.delete(teacher);

        log.debug(DELETED, teacher);
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) {
        log.debug(GET_BY_CATHEDRA_ID, cathedraId);

        Session session = entityManager.unwrap(Session.class);
        @SuppressWarnings("unchecked")
        List<Teacher> teachers = session.createQuery(QUERY_GET_BY_CATHEDRA_ID).setParameter(CATHEDRA_ID, cathedraId).list();

        log.debug(FOUND_BY_CATHEDRA_ID, teachers.size(), TEACHERS);
        return teachers;
    }
}