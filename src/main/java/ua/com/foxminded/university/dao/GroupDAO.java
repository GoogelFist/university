package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Group;

import java.util.List;

public interface GroupDAO extends GenericDaoJpa<Group> {
    List<Group> getByCathedraId(int cathedraId);
}