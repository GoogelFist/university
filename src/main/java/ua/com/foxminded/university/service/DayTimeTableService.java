package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

public interface DayTimeTableService extends GenericServiceJpa<DayTimetable> {
    Page<DayTimetable> getAll(Pageable pageable);

    List<DayTimetable> getByMonthTimetableId(int id);

    Page<DayTimetable> getByMonthTimetableId(int id, Pageable pageable);
}