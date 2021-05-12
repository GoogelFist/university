package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

public interface DayTimetableDAO extends GenericDao<DayTimetable> {
    List<DayTimetable> getByGroupId(int groupId) throws DaoException;

    List<DayTimetable> getByTeacherId(int teacherId) throws DaoException;

    List<DayTimetable> getByMonthTimetableId(int id) throws DaoException;
}