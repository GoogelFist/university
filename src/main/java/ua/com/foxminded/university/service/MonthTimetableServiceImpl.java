package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.*;
import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class MonthTimetableServiceImpl implements MonthTimetableService {
    private static final String LOG_MESSAGE = "MonthTimetableService calls monthTimetableDAO.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String GET_ALL_PAGEABLE = "getAll({})";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String MONTH_TIMETABLE = "monthTimetable";


    private final MonthTimetableDAO monthTimetableDAO;

    @Autowired
    public MonthTimetableServiceImpl(MonthTimetableDAO monthTimetableDAO) {
        this.monthTimetableDAO = monthTimetableDAO;
    }

    @Override
    public void create(MonthTimetable monthTimetable) {
        log.debug(format(LOG_MESSAGE, CREATE), monthTimetable);

        monthTimetableDAO.create(monthTimetable);
    }

    @Override
    public MonthTimetable getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        MonthTimetable monthTimetable = monthTimetableDAO.getById(id);
        if (Objects.isNull(monthTimetable)) {
            String message = format(ERROR_MESSAGE, MONTH_TIMETABLE, id);
            throw new ServiceException(message);
        }
        return monthTimetable;
    }

    @Override
    public List<MonthTimetable> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return monthTimetableDAO.getAll();
    }

    @Override
    public Page<MonthTimetable> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<MonthTimetable> list;

        List<MonthTimetable> monthTimetables = monthTimetableDAO.getAll();

        if (monthTimetables.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, monthTimetables.size());
            list = monthTimetables.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), monthTimetables.size());
    }

    @Override
    public void update(MonthTimetable monthTimetable) {
        log.debug(format(LOG_MESSAGE, UPDATE), monthTimetable);

        monthTimetableDAO.update(monthTimetable);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        monthTimetableDAO.delete(id);
    }
}