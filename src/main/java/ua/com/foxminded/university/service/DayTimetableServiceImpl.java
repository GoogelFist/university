package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.DayTimetableDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

@Service
public class DayTimetableServiceImpl implements DayTimeTableService {
    private final DayTimetableDAO dayTimetableDAO;
    private final GroupService groupService;
    private final TeacherService teacherService;

    private static final Logger logger = LoggerFactory.getLogger(DayTimetableServiceImpl.class);

    @Autowired
    public DayTimetableServiceImpl(DayTimetableDAO dayTimetableDAO, GroupService groupService, TeacherService teacherService) {
        this.dayTimetableDAO = dayTimetableDAO;
        this.groupService = groupService;
        this.teacherService = teacherService;
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
        });
        return dayTimetables;
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
    public List<DayTimetable> getByGroupId(int groupId) {
        logger.debug("DayTimetableService calls dayTimetableDAO.getByGroupId(groupId {})", groupId);
        List<DayTimetable> dayTimetablesByGroupId;
        try {
            dayTimetablesByGroupId = dayTimetableDAO.getByGroupId(groupId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        dayTimetablesByGroupId.forEach(this::setTeacherInDayTimetable);
        return dayTimetablesByGroupId;
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
        dayTimetablesByMonthTimetableId.forEach(dayTimetable -> {
            setGroupInDayTimetable(dayTimetable);
            setTeacherInDayTimetable(dayTimetable);
        });
        return dayTimetablesByMonthTimetableId;
    }

    private void setGroupInDayTimetable(DayTimetable dayTimetable) {
        logger.debug("Call dayTimetable.getGroup().getId()");
        int id = dayTimetable.getGroup().getId();

        logger.debug("Call groupService.getById(id {})", id);
        Group groupById = groupService.getById(id);

        logger.debug("Set {} to the {}", groupById, dayTimetable);
        dayTimetable.setGroup(groupById);
    }

    private void setTeacherInDayTimetable(DayTimetable dayTimetable) {
        logger.debug("Call dayTimetable.getTeacher().getId()");
        int id = dayTimetable.getTeacher().getId();

        logger.debug("Call teacherService.getById(id {})", id);
        Teacher teacherById = teacherService.getById(id);

        logger.debug("Set {} to the {}", teacherById, dayTimetable);
        dayTimetable.setTeacher(teacherById);
    }
}