package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

public interface DayTimeTableService extends GenericService<DayTimetable> {
    Page<DayTimetable> getAll(Pageable pageable);

    Page<DayTimetable> getByGroupId(int groupId, Pageable pageable);

    List<DayTimetable> getByGroupId(int groupId);

    Page<DayTimetable> getByTeacherId(int teacherId, Pageable pageable);

    List<DayTimetable> getByTeacherId(int teacherId);

    List<DayTimetable> getByMonthTimetableId(int id);

    Page<DayTimetable> getByMonthTimetableId(int id, Pageable pageable);
}