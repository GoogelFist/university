package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Student;
import com.github.googelfist.university.entities.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService extends GenericServiceJpa<Student> {
    Page<StudentDto> getAllDto(Pageable pageable);

    Page<StudentDto> getDtoByGroupId(int id, Pageable pageable);

    StudentDto getDtoById(int id);

    void createDto(StudentDto studentDto);

    void updateDto(StudentDto studentDto);
}