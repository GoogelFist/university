package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

@Service
public class MonthTimetableServiceImpl implements MonthTimetableService {
    private final MonthTimetableDAO monthTimetableDAO;
    private final DayTimeTableService dayTimeTableService;

    private static final Logger logger = LoggerFactory.getLogger(MonthTimetableServiceImpl.class);

    @Autowired
    public MonthTimetableServiceImpl(MonthTimetableDAO monthTimetableDAO, DayTimeTableService dayTimeTableService) {
        this.monthTimetableDAO = monthTimetableDAO;
        this.dayTimeTableService = dayTimeTableService;
    }

    @Override
    public void create(MonthTimetable monthTimetable) {
        logger.debug("MonthTimetableService calls monthTimeTableDAO.create({})", monthTimetable);
        try {
            monthTimetableDAO.create(monthTimetable);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public MonthTimetable getById(int id) {
        logger.debug("MonthTimetableService calls monthTimeTableDAO.grtByID(id {})", id);
        MonthTimetable monthTimetableById;
        try {
            monthTimetableById = monthTimetableDAO.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        setDayTimetablesInMonthTimetable(monthTimetableById);
        return monthTimetableById;
    }

    @Override
    public List<MonthTimetable> getAll() {
        logger.debug("MonthTimetableService calls monthTimeTableDAO.getAll()");
        List<MonthTimetable> monthTimetables;
        try {
            monthTimetables = monthTimetableDAO.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        monthTimetables.forEach(this::setDayTimetablesInMonthTimetable);
        return monthTimetables;
    }

    @Override
    public void update(int id, MonthTimetable monthTimetable) {
        logger.debug("MonthTimetableService calls monthTimeTableDAO.update (id {}, {})", id, monthTimetable);
        try {
            monthTimetableDAO.update(id, monthTimetable);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("MonthTimetableService calls monthTimeTableDAO.delete(id {})", id);
        try {
            monthTimetableDAO.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void setDayTimetablesInMonthTimetable(MonthTimetable monthTimetable) {
        logger.debug("Call monthTimetable.getId()");
        int id = monthTimetable.getId();

        logger.debug("Call dayTimeTableService.getByMonthTimetableId(id {})", id);
        List<DayTimetable> dayTimetablesByMonthTimetableId = dayTimeTableService.getByMonthTimetableId(id);

        logger.debug("Set {} to the {}", dayTimetablesByMonthTimetableId, monthTimetable);
        monthTimetable.setDayTimetables(dayTimetablesByMonthTimetableId);
    }
}