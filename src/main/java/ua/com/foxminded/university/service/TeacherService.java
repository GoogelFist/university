package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.entities.dto.TeacherDto;

public interface TeacherService extends GenericServiceJpa<Teacher> {
    Page<TeacherDto> getAllDto(Pageable pageable);

    Page<TeacherDto> getDtoByCathedraId(int id, Pageable pageable);

    TeacherDto getDtoById(int id);

    void createDto(TeacherDto teacherDto);

    void updateDto(TeacherDto teacherDto);
}