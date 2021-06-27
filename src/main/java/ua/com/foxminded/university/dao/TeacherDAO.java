package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

public interface TeacherDAO extends GenericDaoJpa<Teacher> {
    List<Teacher> getByCathedraId(int cathedraId);
}