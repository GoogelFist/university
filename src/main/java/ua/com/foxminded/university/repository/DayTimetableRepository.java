package ua.com.foxminded.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.DayTimetable;

import java.util.List;

public interface DayTimetableRepository extends PagingAndSortingRepository<DayTimetable, Integer> {
    List<DayTimetable> findByMonthTimetableId(int monthTimetableId);

    Page<DayTimetable> findByMonthTimetableId(int monthTimetableId, Pageable pageable);
}
