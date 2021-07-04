package ua.com.foxminded.university.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.Timetable;

public interface TimetableRepository extends PagingAndSortingRepository<Timetable, Integer> {
}