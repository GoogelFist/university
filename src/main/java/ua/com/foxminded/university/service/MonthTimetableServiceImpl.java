package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.repository.MonthTimetableRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

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


    private final MonthTimetableRepository monthTimetableRepository;

    @Autowired
    public MonthTimetableServiceImpl(MonthTimetableRepository monthTimetableRepository) {
        this.monthTimetableRepository = monthTimetableRepository;
    }

    @Override
    public void create(MonthTimetable monthTimetable) {
        log.debug(format(LOG_MESSAGE, CREATE), monthTimetable);

        monthTimetableRepository.save(monthTimetable);
    }

    @Override
    public MonthTimetable getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Optional<MonthTimetable> optionalMonthTimetable = monthTimetableRepository.findById(id);
        if (!optionalMonthTimetable.isPresent()) {
            String message = format(ERROR_MESSAGE, MONTH_TIMETABLE, id);
            throw new ServiceException(message);
        }
        return optionalMonthTimetable.get();
    }

    @Override
    public List<MonthTimetable> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return (List<MonthTimetable>) monthTimetableRepository.findAll();
    }

    @Override
    public Page<MonthTimetable> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        return monthTimetableRepository.findAll(pageable);
    }

    @Override
    public void update(MonthTimetable monthTimetable) {
        log.debug(format(LOG_MESSAGE, UPDATE), monthTimetable);

        monthTimetableRepository.save(monthTimetable);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        monthTimetableRepository.deleteById(id);
    }
}