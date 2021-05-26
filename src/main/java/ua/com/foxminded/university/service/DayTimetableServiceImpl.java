package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.DayTimetableDAO;
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DayTimetableServiceImpl implements DayTimeTableService {
    private final DayTimetableDAO dayTimetableDAO;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final MonthTimetableDAO monthTimetableDAO;

    private static final String SET = "Set {} to the {}";

    private static final Logger logger = LoggerFactory.getLogger(DayTimetableServiceImpl.class);

    @Autowired
    public DayTimetableServiceImpl(DayTimetableDAO dayTimetableDAO, GroupService groupService, TeacherService teacherService, MonthTimetableDAO monthTimetableDAO) {
        this.dayTimetableDAO = dayTimetableDAO;
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.monthTimetableDAO = monthTimetableDAO;
    }

    @Override
    public void create(DayTimetable dayTimetable) {
        logger.debug("DayTimetableService calls dayTimetableDAO.create({})", dayTimetable);
        try {
            dayTimetableDAO.create(dayTimetable);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public DayTimetable getById(int id) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getById({})", id);
        DayTimetable dayTimetableById;
        try {
            dayTimetableById = dayTimetableDAO.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        setGroupInDayTimetable(dayTimetableById);
        setTeacherInDayTimetable(dayTimetableById);
        setMonthTimetableInDayTimetable(dayTimetableById);
        return dayTimetableById;
    }

    @Override
    public List<DayTimetable> getAll() {
        logger.debug("DayTimetableService calls dayTimetableDAO.getAll()");
        List<DayTimetable> dayTimetables;
        try {
            dayTimetables = dayTimetableDAO.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        dayTimetables.forEach(dayTimetable -> {
            setGroupInDayTimetable(dayTimetable);
            setTeacherInDayTimetable(dayTimetable);
            setMonthTimetableInDayTimetable(dayTimetable);
        });
        return dayTimetables;
    }

    @Override
    public Page<DayTimetable> getAll(Pageable pageable) {
        logger.debug("DayTimetableService calls dayTimetableDao.getAll({})", pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DayTimetable> dayTimetables;
        List<DayTimetable> list;
        try {
            dayTimetables = dayTimetableDAO.getAll();
            dayTimetables.sort(Comparator.comparing(DayTimetable::getStartTime));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (dayTimetables.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dayTimetables.size());
            list = dayTimetables.subList(startItem, toIndex);
            list.forEach(this::setMonthTimetableInDayTimetable);
            list.forEach(this::setGroupInDayTimetable);
            list.forEach(this::setTeacherInDayTimetable);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dayTimetables.size());
    }

    @Override
    public void update(int id, DayTimetable dayTimetable) {
        logger.debug("DayTimetableService calls dayTimetableDAO.update(id {}, {})", id, dayTimetable);
        try {
            dayTimetableDAO.update(id, dayTimetable);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("DayTimetableService calls dayTimetableDAO.delete(id {})", id);
        try {
            dayTimetableDAO.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Page<DayTimetable> getByGroupId(int groupId, Pageable pageable) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getByGroupId({}, {})", groupId, pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DayTimetable> dayTimetables;
        List<DayTimetable> list;
        try {
            dayTimetables = dayTimetableDAO.getByGroupId(groupId);
            dayTimetables.forEach(this::setMonthTimetableInDayTimetable);
            dayTimetables.forEach(this::setTeacherInDayTimetable);
            dayTimetables.sort(Comparator.comparing(dayTimetable -> dayTimetable.getMonthTimetable().getDate()));

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (dayTimetables.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dayTimetables.size());
            list = dayTimetables.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dayTimetables.size());
    }

    @Override
    public List<DayTimetable> getByGroupId(int groupId) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getByGroupId(groupId {})", groupId);
        List<DayTimetable> dayTimetablesByGroupId;
        try {
            dayTimetablesByGroupId = dayTimetableDAO.getByGroupId(groupId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        dayTimetablesByGroupId.forEach(this::setTeacherInDayTimetable);
        dayTimetablesByGroupId.forEach(this::setMonthTimetableInDayTimetable);
        return dayTimetablesByGroupId;
    }

    @Override
    public Page<DayTimetable> getByTeacherId(int teacherId, Pageable pageable) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getByTeacher({}, {})", teacherId, pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DayTimetable> dayTimetables;
        List<DayTimetable> list;
        try {
            dayTimetables = dayTimetableDAO.getByTeacherId(teacherId);
            dayTimetables.forEach(this::setMonthTimetableInDayTimetable);
            dayTimetables.forEach(this::setGroupInDayTimetable);
            dayTimetables.sort(Comparator.comparing(dayTimetable -> dayTimetable.getMonthTimetable().getDate()));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (dayTimetables.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dayTimetables.size());
            list = dayTimetables.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dayTimetables.size());
    }

    @Override
    public List<DayTimetable> getByTeacherId(int teacherId) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getByTeacherId(teacherId {})", teacherId);
        List<DayTimetable> dayTimetablesByTeacherId;
        try {
            dayTimetablesByTeacherId = dayTimetableDAO.getByTeacherId(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        dayTimetablesByTeacherId.forEach(this::setGroupInDayTimetable);
        dayTimetablesByTeacherId.forEach(this::setMonthTimetableInDayTimetable);

        return dayTimetablesByTeacherId;
    }

    @Override
    public List<DayTimetable> getByMonthTimetableId(int id) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getByMonthTimetable(id {})", id);
        List<DayTimetable> dayTimetablesByMonthTimetableId;
        try {
            dayTimetablesByMonthTimetableId = dayTimetableDAO.getByMonthTimetableId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        dayTimetablesByMonthTimetableId.forEach(this::setGroupInDayTimetable);
        dayTimetablesByMonthTimetableId.forEach(this::setTeacherInDayTimetable);
        return dayTimetablesByMonthTimetableId;
    }

    @Override
    public Page<DayTimetable> getByMonthTimetableId(int id, Pageable pageable) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getByMonthTimetable({}, {})", id, pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DayTimetable> dayTimetables;
        List<DayTimetable> list;
        try {
            dayTimetables = dayTimetableDAO.getByMonthTimetableId(id);
            dayTimetables.sort(Comparator.comparing(DayTimetable::getStartTime));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (dayTimetables.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dayTimetables.size());
            list = dayTimetables.subList(startItem, toIndex);
            dayTimetables.forEach(this::setTeacherInDayTimetable);
            dayTimetables.forEach(this::setGroupInDayTimetable);

        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dayTimetables.size());
    }

    private void setGroupInDayTimetable(DayTimetable dayTimetable) {
        logger.debug("Call dayTimetable.getGroup().getId()");
        int id = dayTimetable.getGroup().getId();

        logger.debug("Call groupService.getById(id {})", id);
        Group groupById = groupService.getById(id);

        logger.debug(SET, groupById, dayTimetable);
        dayTimetable.setGroup(groupById);
    }

    private void setTeacherInDayTimetable(DayTimetable dayTimetable) {
        logger.debug("Call dayTimetable.getTeacher().getId()");
        int id = dayTimetable.getTeacher().getId();

        logger.debug("Call teacherService.getById(id {})", id);
        Teacher teacherById = teacherService.getById(id);

        logger.debug(SET, teacherById, dayTimetable);
        dayTimetable.setTeacher(teacherById);
    }

    private void setMonthTimetableInDayTimetable(DayTimetable dayTimetable) {
        logger.debug("Call dayTimetable.getMonthTimetable().getId()");
        int id = dayTimetable.getMonthTimetable().getId();

        logger.debug("Call monthTimetableService.getById({})", id);
        MonthTimetable monthTimetableServiceById;

        try {
            monthTimetableServiceById = monthTimetableDAO.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        logger.debug(SET, id, monthTimetableServiceById);
        dayTimetable.setMonthTimetable(monthTimetableServiceById);
    }
}