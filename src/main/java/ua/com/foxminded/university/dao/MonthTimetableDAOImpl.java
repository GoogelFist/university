package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.MonthTimetable;

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


    private final SessionFactory sessionFactory;

    @Autowired
    public MonthTimetableDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(MonthTimetable monthTimetable) {
        log.debug(CREATE, monthTimetable);

        sessionFactory.getCurrentSession().saveOrUpdate(monthTimetable);

        log.debug(CREATED, monthTimetable);
    }

    @Override
    public MonthTimetable getById(int id) {
        log.debug(GET_BY_ID, id);

        MonthTimetable monthTimetable = sessionFactory.getCurrentSession().get(MonthTimetable.class, id);

        log.debug(FOUND_BY_ID, monthTimetable);
        return monthTimetable;
    }

    @Override
    public List<MonthTimetable> getAll() {
        log.debug(GET_ALL);

        @SuppressWarnings("unchecked")
        List<MonthTimetable> monthTimetables = sessionFactory.getCurrentSession().createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, monthTimetables.size(), MONTH_TIMETABLES);
        return monthTimetables;
    }

    @Override
    public void update(MonthTimetable monthTimetable) {
        log.debug(UPDATE, monthTimetable);

        sessionFactory.getCurrentSession().update(monthTimetable);

        log.debug(UPDATED, monthTimetable);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = sessionFactory.getCurrentSession();
        MonthTimetable monthTimetable = session.get(MonthTimetable.class, id);

        if (monthTimetable != null) {
            session.delete(monthTimetable);
            log.debug(DELETED, MONTH_TIMETABLE);
        }
    }
}