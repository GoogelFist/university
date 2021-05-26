package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

public interface StudentService extends GenericService<Student> {
    Page<Student> getAll(Pageable pageable);

    List<Student> getByGroupId(int groupId);

    Page<Student> getByGroupId(int groupId, Pageable pageable);

    void assignToGroup(int groupId, int studentId);

    void updateAssignment(int groupId, int studentId);

    void deleteAssignment(int studentId);
}