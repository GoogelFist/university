package com.github.googelfist.university.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.github.googelfist.university.entities.Timetable;

public interface TimetableRepository extends PagingAndSortingRepository<Timetable, Integer> {
}