package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

public interface DayTimetableDAO extends GenericDaoJpa<DayTimetable> {
    List<DayTimetable> getByMonthTimetableId(int id);
}