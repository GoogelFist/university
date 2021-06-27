package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.Student;

import java.util.List;


@Slf4j
@Repository
public class StudentDAOImpl implements StudentDAO {
    private static final String STUDENTS = "students";
    private static final String QUERY_GET_BY_GROUP_ID = "from Student student where student.group.id = :groupId order by id";
    private static final String GROUP_ID = "groupId";
    private static final String QUERY_GET_ALL = "from Student order by id";

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
    private static final String GET_BY_GROUP_ID = "getByGroupId {}";
    private static final String FOUND_BY_GROUP_ID = "Found {} {}";


    private final SessionFactory sessionFactory;

    @Autowired
    public StudentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Student student) {
        log.debug(CREATE, student);

        sessionFactory.getCurrentSession().saveOrUpdate(student);

        log.debug(CREATED, student);
    }

    @Override
    public Student getById(int id) {
        log.debug(GET_BY_ID, id);

        Student student = sessionFactory.getCurrentSession().get(Student.class, id);

        log.debug(FOUND_BY_ID, student);
        return student;
    }

    @Override
    public List<Student> getAll() {
        log.debug(GET_ALL);

        @SuppressWarnings("unchecked")
        List<Student> students = sessionFactory.getCurrentSession().createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, students.size(), STUDENTS);
        return students;
    }

    @Override
    public void update(Student student) {
        log.debug(UPDATE, student);

        sessionFactory.getCurrentSession().update(student);

        log.debug(UPDATED, student);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = sessionFactory.getCurrentSession();
        Student student = session.get(Student.class, id);

        if (student != null) {
            session.delete(student);
            log.debug(DELETED, student);
        }
    }

    @Override
    public List<Student> getByGroupId(int groupId) {
        log.debug(GET_BY_GROUP_ID, groupId);

        @SuppressWarnings("unchecked")
        List<Student> students = sessionFactory.getCurrentSession().createQuery(QUERY_GET_BY_GROUP_ID).setParameter(GROUP_ID, groupId).list();

        log.debug(FOUND_BY_GROUP_ID, students.size(), STUDENTS);
        return students;
    }
}