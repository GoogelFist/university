package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.DayTimetableDAO;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class DayTimetableServiceImpl implements DayTimeTableService {
    private static final String LOG_MESSAGE = "DayTimetableService calls dayTimetableDAO.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_BY_MONTH_TIMETABLE_ID_PAGEABLE = "getByMonthTimetable(id {}, {})";
    private static final String GET_BY_MONTH_TIMETABLE_ID = "getByMonthTimetable(id {})";
    private static final String GET_ALL = "getAll()";
    private static final String GET_ALL_PAGEABLE = "getAll({})";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String DAY_TIMETABLE = "dayTimetable";


    private final DayTimetableDAO dayTimetableDAO;

    @Autowired
    public DayTimetableServiceImpl(DayTimetableDAO dayTimetableDAO) {
        this.dayTimetableDAO = dayTimetableDAO;
    }

    @Override
    public void create(DayTimetable dayTimetable) {
        log.debug(format(LOG_MESSAGE, CREATE), dayTimetable);

        dayTimetableDAO.create(dayTimetable);
    }

    @Override
    public DayTimetable getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        DayTimetable dayTimetable = dayTimetableDAO.getById(id);
        if (Objects.isNull(dayTimetable)) {
            String message = format(ERROR_MESSAGE, DAY_TIMETABLE, id);
            throw new ServiceException(message);
        }
        return dayTimetable;
    }

    @Override
    public List<DayTimetable> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return dayTimetableDAO.getAll();
    }

    @Override
    public Page<DayTimetable> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DayTimetable> list;

        List<DayTimetable> dayTimetables = dayTimetableDAO.getAll();

        if (dayTimetables.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dayTimetables.size());
            list = dayTimetables.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dayTimetables.size());
    }

    @Override
    public void update(DayTimetable dayTimetable) {
        log.debug(format(LOG_MESSAGE, UPDATE), dayTimetable);

        dayTimetableDAO.update(dayTimetable);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        dayTimetableDAO.delete(id);
    }

    @Override
    public List<DayTimetable> getByMonthTimetableId(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_MONTH_TIMETABLE_ID), id);

        return dayTimetableDAO.getByMonthTimetableId(id);
    }

    @Override
    public Page<DayTimetable> getByMonthTimetableId(int id, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_MONTH_TIMETABLE_ID_PAGEABLE), id, pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DayTimetable> list;

        List<DayTimetable> dayTimetables = dayTimetableDAO.getByMonthTimetableId(id);

        if (dayTimetables.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dayTimetables.size());
            list = dayTimetables.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dayTimetables.size());
    }
}