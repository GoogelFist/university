package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.dto.StudentDto;

public interface StudentService extends GenericServiceJpa<Student> {
    Page<StudentDto> getAllDto(Pageable pageable);

    Page<StudentDto> getDtoByGroupId(int id, Pageable pageable);

    StudentDto getDtoById(int id);

    void createDto(StudentDto studentDto);

    void updateDto(StudentDto studentDto);
}