package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Student;

import java.util.List;

public interface StudentDAO extends GenericDao<Student> {
    List<Student> getByGroupId(int groupId);

    void assignToGroup(int groupId, int studentId);

    void updateAssignment(int groupId, int studentId);

    void deleteAssignment(int studentId);
}