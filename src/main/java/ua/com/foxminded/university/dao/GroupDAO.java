package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Group;

import java.util.List;

public interface GroupDAO extends GenericDao<Group> {
    List<Group> getByCathedraId(int cathedraId);

    void assignToCathedra(int cathedraId, int groupId);

    void updateAssignment(int cathedraId, int groupId);

    void deleteAssignment(int groupId);
}