package ua.com.foxminded.university.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.MonthTimetable;

public interface MonthTimetableRepository extends PagingAndSortingRepository<MonthTimetable, Integer> {
}
