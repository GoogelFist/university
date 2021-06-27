package ua.com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

@Slf4j
@Repository
public class DayTimetableDAOIml implements DayTimetableDAO {
    private static final String DAY_TIMETABLES = "dayTimetables";
    private static final String MONTH_TIMETABLE_ID = "monthTimetableId";

    private static final String QUERY_GET_BY_MONTH_TIMETABLE_ID = "from DayTimetable dayTimetable where dayTimetable.monthTimetable.id = :monthTimetableId order by id";
    private static final String QUERY_GET_ALL = "from DayTimetable order by id";

    private static final String CREATE = "create({})";
    private static final String CREATED = "{} created";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_BY_MONTH_TIMETABLE_ID = "getByMonthTimetableId({})";
    private static final String FOUND_BY_ID = "Found {}";
    private static final String GET_ALL = "getAll()";
    private static final String FOUND_ALL = "Found {} {}";
    private static final String UPDATE = "update({})";
    private static final String UPDATED = "{} was updated";
    private static final String DELETE = "delete(Id {})";
    private static final String DELETED = "{} was deleted";
    private static final String FOUND_BY_MONTH_TIMETABLE = "Found {} {}";


    private final SessionFactory sessionFactory;

    @Autowired
    public DayTimetableDAOIml(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(DayTimetable dayTimetable) {
        log.debug(CREATE, dayTimetable);

        sessionFactory.getCurrentSession().saveOrUpdate(dayTimetable);

        log.debug(CREATED, dayTimetable);
    }

    @Override
    public DayTimetable getById(int id) {
        log.debug(GET_BY_ID, id);

        DayTimetable dayTimetable = sessionFactory.getCurrentSession().get(DayTimetable.class, id);

        log.debug(FOUND_BY_ID, dayTimetable);
        return dayTimetable;
    }

    @Override
    public List<DayTimetable> getAll() {
        log.debug(GET_ALL);

        @SuppressWarnings("unchecked")
        List<DayTimetable> dayTimetables = sessionFactory.getCurrentSession().createQuery(QUERY_GET_ALL).list();

        log.debug(FOUND_ALL, dayTimetables.size(), DAY_TIMETABLES);
        return dayTimetables;
    }

    @Override
    public void update(DayTimetable dayTimetable) {
        log.debug(UPDATE, dayTimetable);

        sessionFactory.getCurrentSession().update(dayTimetable);

        log.debug(UPDATED, dayTimetable);
    }

    @Override
    public void delete(int id) {
        log.debug(DELETE, id);

        Session session = sessionFactory.getCurrentSession();
        DayTimetable dayTimetable = session.get(DayTimetable.class, id);

        if (dayTimetable != null) {
            session.delete(dayTimetable);
            log.debug(DELETED, dayTimetable);
        }
    }

    @Override
    public List<DayTimetable> getByMonthTimetableId(int id) {
        log.debug(GET_BY_MONTH_TIMETABLE_ID, id);

        @SuppressWarnings("unchecked")
        List<DayTimetable> dayTimetables = sessionFactory.getCurrentSession().createQuery(QUERY_GET_BY_MONTH_TIMETABLE_ID).setParameter(MONTH_TIMETABLE_ID, id).list();

        log.debug(FOUND_BY_MONTH_TIMETABLE, dayTimetables.size(), DAY_TIMETABLES);
        return dayTimetables;
    }
}