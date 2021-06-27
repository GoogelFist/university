package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.Cathedra;

import java.util.List;

@Slf4j
@Repository
public class CathedraDAOImpl implements CathedraDAO {
    private static final String CATHEDRAS = "cathedras";
    private static final String QUERY_GET_ALL = "from Cathedra order by id";

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


    private final SessionFactory sessionFactory;

    @Autowired
    public CathedraDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Cathedra cathedra) {
        log.debug(CREATE, cathedra);

        sessionFactory.getCurrentSession().saveOrUpdate(cathedra);

        log.debug(CREATED, cathedra);
    }

    @Override
    public Cathedra getById(int id) {
        log.debug(GET_BY_ID, id);

        Cathedra cathedra = sessionFactory.getCurrentSession().get(Cathedra.class, id);

        log.debug(FOUND_BY_ID, cathedra);
        return cathedra;
    }

    @Override
    public List<Cathedra> getAll() {
        log.debug(GET_ALL);

        @SuppressWarnings("unchecked")
        List<Cathedra> cathedras = sessionFactory.getCurrentSession().createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, cathedras.size(), CATHEDRAS);
        return cathedras;
    }

    @Override
    public void update(Cathedra cathedra) {
        log.debug(UPDATE, cathedra);

        sessionFactory.getCurrentSession().update(cathedra);

        log.debug(UPDATED, cathedra);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = sessionFactory.getCurrentSession();
        Cathedra cathedra = session.get(Cathedra.class, id);

        if (cathedra != null) {
            session.delete(cathedra);
            log.debug(DELETED, cathedra);
        }
    }
}