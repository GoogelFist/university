package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.MonthTimetable;

public interface MonthTimetableService extends GenericServiceJpa<MonthTimetable> {
    Page<MonthTimetable> getAll(Pageable pageable);
}
