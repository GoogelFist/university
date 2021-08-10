package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Timetable;
import com.github.googelfist.university.entities.dto.TimetableDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TimetableService extends GenericServiceJpa<Timetable> {
    Page<TimetableDto> getAllDto(Pageable pageable);

    TimetableDto getDtoById(int id);

    void createDto(TimetableDto timetableDto);

    void updateDto(TimetableDto timetableDto);
}