package ua.com.foxminded.university.service;

import ua.com.foxminded.university.entities.Timetable;
import ua.com.foxminded.university.entities.dto.TimetableDto;

import java.util.List;

public interface TimetableService extends GenericServiceJpa<Timetable> {
    List<TimetableDto> getAllDto();

    TimetableDto getDtoById(int id);

    void createDto(TimetableDto timetableDto);

    void updateDto(TimetableDto timetableDto);
}