package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Timetable;
import ua.com.foxminded.university.entities.dto.TimetableDto;

public interface TimetableService extends GenericServiceJpa<Timetable> {
    Page<TimetableDto> getAllDto(Pageable pageable);

    TimetableDto getDtoById(int id);

    void createDto(TimetableDto timetableDto);

    void updateDto(TimetableDto timetableDto);
}