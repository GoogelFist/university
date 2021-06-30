package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.MonthTimetable;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class MonthTimetableDAOImpl implements MonthTimetableDAO {
    private static final String MONTH_TIMETABLES = "month_timetables";
    private static final String MONTH_TIMETABLE = "month_timetable";
    private static final String QUERY_GET_ALL = "from MonthTimetable order by id";

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


    private final EntityManager entityManager;

    @Autowired
    public MonthTimetableDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(MonthTimetable monthTimetable) {
        log.debug(CREATE, monthTimetable);

        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(monthTimetable);

        log.debug(CREATED, monthTimetable);
    }

    @Override
    public MonthTimetable getById(int id) {
        log.debug(GET_BY_ID, id);

        Session session = entityManager.unwrap(Session.class);
        MonthTimetable monthTimetable = session.get(MonthTimetable.class, id);

        log.debug(FOUND_BY_ID, monthTimetable);
        return monthTimetable;
    }

    @Override
    public List<MonthTimetable> getAll() {
        log.debug(GET_ALL);

        Session session = entityManager.unwrap(Session.class);
        @SuppressWarnings("unchecked")
        List<MonthTimetable> monthTimetables = session.createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, monthTimetables.size(), MONTH_TIMETABLES);
        return monthTimetables;
    }

    @Override
    public void update(MonthTimetable monthTimetable) {
        log.debug(UPDATE, monthTimetable);

        Session session = entityManager.unwrap(Session.class);
        session.update(monthTimetable);

        log.debug(UPDATED, monthTimetable);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = entityManager.unwrap(Session.class);
        MonthTimetable monthTimetable = session.get(MonthTimetable.class, id);

        if (monthTimetable != null) {
            session.delete(monthTimetable);
            log.debug(DELETED, MONTH_TIMETABLE);
        }
    }
}