package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Student;

import java.util.List;

public interface StudentDAO extends GenericDaoJpa<Student> {
    List<Student> getByGroupId(int groupId);
}