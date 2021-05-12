package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Group;

import java.util.List;

public interface GroupDAO extends GenericDao<Group> {
    List<Group> getByCathedraId(int cathedraId) throws DaoException;

    void assignToCathedra(int cathedraId, int groupId) throws DaoException;

    void updateAssignment(int cathedraId, int groupId) throws DaoException;

    void deleteAssignment(int groupId) throws DaoException;
}