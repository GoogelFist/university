package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Teacher;
import com.github.googelfist.university.entities.dto.TeacherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService extends GenericServiceJpa<Teacher> {
    Page<TeacherDto> getAllDto(Pageable pageable);

    Page<TeacherDto> getDtoByCathedraId(int id, Pageable pageable);

    TeacherDto getDtoById(int id);

    void createDto(TeacherDto teacherDto);

    void updateDto(TeacherDto teacherDto);
}