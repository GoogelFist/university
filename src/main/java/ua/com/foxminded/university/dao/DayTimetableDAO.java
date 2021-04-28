package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

public interface DayTimetableDAO extends GenericDao<DayTimetable> {
    List<DayTimetable> getByGroupId(int groupId);

    List<DayTimetable> getByTeacherId(int teacherId);

    List<DayTimetable> getByMonthTimetableId(int id);
}