package ua.com.foxminded.university.service;

import ua.com.foxminded.university.entities.Group;

import java.util.List;

public interface GroupService extends GenericService<Group> {
    List<Group> getByCathedraId(int cathedraId);

    void assignToCathedra(int cathedraId, int groupId);

    void updateAssignment(int cathedraId, int groupId);

    void deleteAssignment(int cathedraId);
}
