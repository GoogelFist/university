package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupDAO groupDAO;
    private final StudentService studentService;
    private final CathedraDAO cathedraDAO;

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO, StudentService studentService, CathedraDAO cathedraDAO) {
        this.groupDAO = groupDAO;
        this.studentService = studentService;
        this.cathedraDAO = cathedraDAO;
    }

    @Override
    public void create(Group group) {
        logger.debug("GroupService calls groupDao.create({})", group);
        try {
            groupDAO.create(group);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Group getById(int id) {
        logger.debug("GroupService calls groupDao.getById(id {})", id);
        Group group;
        try {
            group = groupDAO.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        setStudentsInGroup(group);
        return group;
    }

    @Override
    public List<Group> getAll() {
        logger.debug("GroupService calls groupDao.getAll()");
        List<Group> groups;
        try {
            groups = groupDAO.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        groups.forEach(this::setStudentsInGroup);
        return groups;
    }

    @Override
    public void update(int id, Group group) {
        logger.debug("GroupService calls groupDao.update(id {}, {})", id, group);
        try {
            groupDAO.update(id, group);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("GroupService calls groupDao.delete(id {})", id);
        try {
            groupDAO.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Group> getByCathedraId(int cathedraId) {
        logger.debug("GroupService calls groupDao.getByCathedraId(id {})", cathedraId);
        List<Group> groupsByCathedraId;
        try {
            groupsByCathedraId = groupDAO.getByCathedraId(cathedraId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        groupsByCathedraId.forEach(this::setStudentsInGroup);
        return groupsByCathedraId;
    }

    @Override
    public void assignToCathedra(int cathedraId, int groupId) {
        logger.debug("GroupService calls groupDao.assignToCathedra(cathedraId {}, groupId {})", cathedraId, groupId);
        try {
            groupDAO.assignToCathedra(cathedraId, groupId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateAssignment(int cathedraId, int groupId) {
        checkingCathedraInDao(cathedraId);
        checkingGroupInDao(groupId);

        logger.debug("GroupService calls groupDao.updateAssignment(cathedraId {}, groupId {})", cathedraId, groupId);
        try {
            groupDAO.updateAssignment(cathedraId, groupId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteAssignment(int cathedraId) {
        logger.debug("GroupService calls groupDao.deleteAssignment(cathedraId {})", cathedraId);
        try {
            groupDAO.deleteAssignment(cathedraId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void setStudentsInGroup(Group group) {
        logger.debug("Call group.getId()");
        int id = group.getId();

        logger.debug("Call studentService.getByGroupId(id {})", id);
        List<Student> studentsByGroupId = studentService.getByGroupId(id);

        logger.debug("Set {} to the {}", studentsByGroupId, group);
        group.setStudents(studentsByGroupId);
    }

    private void checkingCathedraInDao(int cathedraId) {
        logger.debug("Checking the presence of a cathedra");
        try {
            cathedraDAO.getById(cathedraId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        logger.trace("Cathedra is present");
    }

    private void checkingGroupInDao(int groupId) {
        logger.debug("Checking the presence of a group");
        try {
            groupDAO.getById(groupId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        logger.debug("Group is present");
    }
}