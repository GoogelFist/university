package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.TeacherDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.Comparator;
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
        setCathedraToTeacher(teacherById);
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
    public Page<Teacher> getAll(Pageable pageable) {
        logger.debug("TeacherService calls teacherDAO.getAll({})", pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Teacher> teachers;
        List<Teacher> list;
        try {
            teachers = teacherDAO.getAll();
            teachers.sort(Comparator.comparing(Teacher::getId));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (teachers.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, teachers.size());
            list = teachers.subList(startItem, toIndex);
            list.forEach(this::setCathedraToTeacher);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), teachers.size());
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
        checkingCathedraInDao(cathedraId);

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
    public Page<Teacher> getByCathedraId(int cathedraId, Pageable pageable) {
        checkingCathedraInDao(cathedraId);
        logger.debug("TeacherService calls teacherDAO.getByCathedraId({}, {})", cathedraId, pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Teacher> teachers;
        List<Teacher> list;
        try {
            teachers = teacherDAO.getByCathedraId(cathedraId);
            teachers.sort(Comparator.comparing(Teacher::getId));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (teachers.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, teachers.size());
            list = teachers.subList(startItem, toIndex);
            list.forEach(this::setCathedraToTeacher);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), teachers.size());
    }

    @Override
    public void assignToCathedra(int cathedraId, int teacherId) {
        checkingCathedraInDao(cathedraId);

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

    private void checkingCathedraInDao(int cathedraId) {
        logger.debug("Checking the presence of a cathedra");
        try {
            cathedraDAO.getById(cathedraId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        logger.debug("Cathedra is present");
    }

    private void setCathedraToTeacher(Teacher teacher) {
        logger.debug("Call teacher.getCathedra().getId()");
        int teacherCathedraId = teacher.getCathedra().getId();
        Cathedra cathedra;
        logger.debug("cathedraDAO.getById {}", teacherCathedraId);
        try {
            cathedra = cathedraDAO.getById(teacherCathedraId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        logger.debug("Set {} to {}", cathedra, teacher);
        teacher.setCathedra(cathedra);
    }
}