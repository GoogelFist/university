package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.Teacher;

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


    private final SessionFactory sessionFactory;

    @Autowired
    public TeacherDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Teacher teacher) {
        log.debug(CREATE, teacher);

        sessionFactory.getCurrentSession().saveOrUpdate(teacher);

        log.debug(CREATED, teacher);
    }

    @Override
    public Teacher getById(int id) {
        log.debug(GET_BY_ID, id);

        Teacher teacher = sessionFactory.getCurrentSession().get(Teacher.class, id);

        log.debug(FOUND_BY_ID, teacher);
        return teacher;
    }

    @Override
    public List<Teacher> getAll() {
        log.debug(GET_ALL);

        @SuppressWarnings("unchecked")
        List<Teacher> teachers = sessionFactory.getCurrentSession().createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, teachers.size(), TEACHERS);
        return teachers;
    }

    @Override
    public void update(Teacher teacher) {
        log.debug(UPDATE, teacher);

        sessionFactory.getCurrentSession().update(teacher);

        log.debug(UPDATED, teacher);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = sessionFactory.getCurrentSession();
        Teacher teacher = session.get(Teacher.class, id);

        if (teacher != null) {
            session.delete(teacher);
            log.debug(DELETED, teacher);
        }
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) {
        log.debug(GET_BY_CATHEDRA_ID, cathedraId);

        @SuppressWarnings("unchecked")
        List<Teacher> teachers = sessionFactory.getCurrentSession().createQuery(QUERY_GET_BY_CATHEDRA_ID).setParameter(CATHEDRA_ID, cathedraId).list();

        log.debug(FOUND_BY_CATHEDRA_ID, teachers.size(), TEACHERS);
        return teachers;
    }
}