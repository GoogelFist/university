package ua.com.foxminded.university.service;

import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

public interface DayTimeTableService extends GenericService<DayTimetable> {
    List<DayTimetable> getByGroupId(int groupId);

    List<DayTimetable> getByTeacherId(int teacherId);

    List<DayTimetable> getByMonthTimetableId(int id);
}