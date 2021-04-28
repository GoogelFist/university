package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupDAO groupDAO;
    private final StudentService studentService;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO, StudentService studentService) {
        this.groupDAO = groupDAO;
        this.studentService = studentService;
    }

    @Override
    public void create(Group group) {
        groupDAO.create(group);
    }

    @Override
    public Group getById(int id) {
        Group group = groupDAO.getById(id);
        setStudentsInGroup(group);
        return group;
    }

    @Override
    public List<Group> getAll() {
        List<Group> groups = groupDAO.getAll();
        groups.forEach(this::setStudentsInGroup);
        return groups;
    }

    @Override
    public void update(int id, Group group) {
        groupDAO.update(id, group);
    }

    @Override
    public void delete(int id) {
        groupDAO.delete(id);
    }

    @Override
    public List<Group> getByCathedraId(int cathedraId) {
        List<Group> groupsByCathedraId = groupDAO.getByCathedraId(cathedraId);
        groupsByCathedraId.forEach(this::setStudentsInGroup);
        return groupsByCathedraId;
    }

    @Override
    public void assignToCathedra(int cathedraId, int groupId) {
        groupDAO.assignToCathedra(cathedraId, groupId);
    }

    @Override
    public void updateAssignment(int cathedraId, int groupId) {
        groupDAO.updateAssignment(cathedraId, groupId);
    }

    @Override
    public void deleteAssignment(int cathedraId) {
        groupDAO.deleteAssignment(cathedraId);
    }

    private void setStudentsInGroup(Group group) {
        int id = group.getId();
        List<Student> studentsByGroupId = studentService.getByGroupId(id);
        group.setStudents(studentsByGroupId);
    }
}