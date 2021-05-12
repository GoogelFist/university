package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

public interface TeacherDAO extends GenericDao<Teacher> {
    List<Teacher> getByCathedraId(int cathedraId) throws DaoException;

    void assignToCathedra(int cathedraId, int teacherId) throws DaoException;

    void updateAssignment(int cathedraId, int teacherId) throws DaoException;

    void deleteAssignment(int teacherId) throws DaoException;
}