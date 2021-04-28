package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

public interface TeacherDAO extends GenericDao<Teacher> {
    List<Teacher> getByCathedraId(int cathedraId);

    void assignToCathedra(int cathedraId, int teacherId);

    void updateAssignment(int cathedraId, int teacherId);

    void deleteAssignment(int teacherId);
}