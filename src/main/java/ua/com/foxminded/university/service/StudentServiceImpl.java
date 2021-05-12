package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;
    private final GroupDAO groupDAO;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, GroupDAO groupDAO) {
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
    }

    @Override
    public void create(Student student) {
        logger.debug("StudentService calls studentDao.create({})", student);
        try {
            studentDAO.create(student);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Student getById(int id) {
        logger.debug("StudentService calls studentDao.getById(id {})", id);
        Student studentById;
        try {
            studentById = studentDAO.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return studentById;
    }

    @Override
    public List<Student> getAll() {
        logger.debug("StudentService calls studentDao.getAll()");
        List<Student> allStudents;
        try {
            allStudents = studentDAO.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return allStudents;
    }

    @Override
    public void update(int id, Student student) {
        logger.debug("StudentService calls studentDao.update(id {}, {})", id, student);
        try {
            studentDAO.update(id, student);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("StudentService calls studentDao.delete(id {})", id);
        try {
            studentDAO.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Student> getByGroupId(int groupId) {
        logger.debug("StudentService calls studentDao.getByGroupId(groupId {})", groupId);
        List<Student> studentsByGroupId;
        try {
            studentsByGroupId = studentDAO.getByGroupId(groupId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return studentsByGroupId;
    }

    @Override
    public void assignToGroup(int groupId, int studentId) {
        logger.debug("StudentService calls studentDao.assignToGroup(groupId {}, studentId {})", groupId, studentId);
        try {
            studentDAO.assignToGroup(groupId, studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateAssignment(int groupId, int studentId) {
        checkingGroupInDao(groupId);
        checkingStudentInDao(studentId);

        logger.debug("StudentService calls studentDao.updateAssignment(groupId {}, studentId {})", groupId, studentId);
        try {
            studentDAO.updateAssignment(groupId, studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteAssignment(int studentId) {
        logger.debug("StudentService calls studentDao.deleteAssignment(studentId {})", studentId);
        try {
            studentDAO.deleteAssignment(studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
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

    private void checkingStudentInDao(int studentId) {
        logger.debug("Checking the presence of a student");
        try {
            studentDAO.getById(studentId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        logger.debug("Student is present");
    }
}