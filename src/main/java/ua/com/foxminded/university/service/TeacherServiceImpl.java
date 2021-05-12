package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.TeacherDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherDAO teacherDAO;
    private final CathedraDAO cathedraDAO;

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, CathedraDAO cathedraDAO) {
        this.teacherDAO = teacherDAO;
        this.cathedraDAO = cathedraDAO;
    }

    @Override
    public void create(Teacher teacher) {
        logger.debug("TeacherService calls teacherDAO.create({})", teacher);
        try {
            teacherDAO.create(teacher);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Teacher getById(int id) {
        logger.debug("TeacherService calls teacherDAO.getById(id {})", id);
        Teacher teacherById;
        try {
            teacherById = teacherDAO.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return teacherById;
    }

    @Override
    public List<Teacher> getAll() {
        logger.debug("TeacherService calls teacherDAO.grtAll()");
        List<Teacher> allTeachers;
        try {
            allTeachers = teacherDAO.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return allTeachers;
    }

    @Override
    public void update(int id, Teacher teacher) {
        logger.debug("TeacherService calls teacherDAO.update(id {}, {})", id, teacher);
        try {
            teacherDAO.update(id, teacher);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("TeacherService calls teacherDAO.delete(id {})", id);
        try {
            teacherDAO.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) {
        logger.debug("TeacherService calls teacherDAO.getByCathedraId(id {})", cathedraId);
        List<Teacher> teachersByCathedraId;
        try {
            teachersByCathedraId = teacherDAO.getByCathedraId(cathedraId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return teachersByCathedraId;
    }

    @Override
    public void assignToCathedra(int cathedraId, int teacherId) {
        logger.debug("TeacherService calls teacherDAO.assignToCathedra(cathedraId {}, teacherId {})", cathedraId, teacherId);
        try {
            teacherDAO.assignToCathedra(cathedraId, teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateAssignment(int cathedraId, int teacherId) {
        checkingCathedraInDao(cathedraId);
        checkingTeacherInDao(teacherId);

        logger.debug("TeacherService calls teacherDAO.updateAssignment(cathedraId {}, teacherId {})", cathedraId, teacherId);
        try {
            teacherDAO.updateAssignment(cathedraId, teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteAssignment(int teacherId) {
        logger.debug("TeacherService calls teacherDAO.deleteAssignment(teacherId {})", teacherId);
        try {
            teacherDAO.deleteAssignment(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void checkingTeacherInDao(int teacherId) {
        logger.debug("Checking the presence of a teacher");
        try {
            teacherDAO.getById(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        logger.debug("Teacher is present");
    }

    private void checkingCathedraInDao(int cathedraId) {
        logger.debug("Checking the presence of a cathedra");
        try {
            cathedraDAO.getById(cathedraId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        logger.debug("Cathedra is present");
    }
}