package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

public interface StudentDAO extends GenericDao<Student> {
    List<Student> getByGroupId(int groupId) throws DaoException;

    void assignToGroup(int groupId, int studentId) throws DaoException;

    void updateAssignment(int groupId, int studentId) throws DaoException;

    void deleteAssignment(int studentId) throws DaoException;
}