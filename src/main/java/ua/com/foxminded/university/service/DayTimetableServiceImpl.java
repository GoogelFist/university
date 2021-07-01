package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.repository.DayTimetableRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

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


    private final DayTimetableRepository dayTimetableRepository;

    @Autowired
    public DayTimetableServiceImpl(DayTimetableRepository dayTimetableRepository) {
        this.dayTimetableRepository = dayTimetableRepository;
    }

    @Override
    public void create(DayTimetable dayTimetable) {
        log.debug(format(LOG_MESSAGE, CREATE), dayTimetable);

        dayTimetableRepository.save(dayTimetable);
    }

    @Override
    public DayTimetable getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Optional<DayTimetable> optionalDayTimetable = dayTimetableRepository.findById(id);
        if (!optionalDayTimetable.isPresent()) {
            String message = format(ERROR_MESSAGE, DAY_TIMETABLE, id);
            throw new ServiceException(message);
        }
        return optionalDayTimetable.get();
    }

    @Override
    public List<DayTimetable> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return (List<DayTimetable>) dayTimetableRepository.findAll();
    }

    @Override
    public Page<DayTimetable> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        return dayTimetableRepository.findAll(pageable);
    }

    @Override
    public void update(DayTimetable dayTimetable) {
        log.debug(format(LOG_MESSAGE, UPDATE), dayTimetable);

        dayTimetableRepository.save(dayTimetable);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        dayTimetableRepository.deleteById(id);
    }

    @Override
    public List<DayTimetable> getByMonthTimetableId(int monthTimetableId) {
        log.debug(format(LOG_MESSAGE, GET_BY_MONTH_TIMETABLE_ID), monthTimetableId);

        return dayTimetableRepository.findByMonthTimetableId(monthTimetableId);
    }

    @Override
    public Page<DayTimetable> getByMonthTimetableId(int monthTimetableId, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_MONTH_TIMETABLE_ID_PAGEABLE), monthTimetableId, pageable);

        return dayTimetableRepository.findByMonthTimetableId(monthTimetableId, pageable);
    }
}