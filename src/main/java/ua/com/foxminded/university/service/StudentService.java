package ua.com.foxminded.university.service;

import ua.com.foxminded.university.entities.Student;

import java.util.List;

public interface StudentService extends GenericService<Student> {
    List<Student> getByGroupId(int groupId);

    void assignToGroup(int groupId, int studentId);

    void updateAssignment(int groupId, int studentId);

    void deleteAssignment(int studentId);
}