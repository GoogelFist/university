package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.TeacherDAO;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private static final String LOG_MESSAGE = "TeacherService calls teacherDAO.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String GET_ALL_PAGEABLE = "getAll({})";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";
    private static final String GET_BY_CATHEDRA_ID = "getByCathedraId({})";
    private static final String GET_BY_CATHEDRA_ID_PAGEABLE = "getByCathedraId({}, {})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String TEACHER = "teacher";


    private final TeacherDAO teacherDAO;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public void create(Teacher teacher) {
        log.debug(format(LOG_MESSAGE, CREATE), teacher);

        teacherDAO.create(teacher);
    }

    @Override
    public Teacher getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Teacher teacher = teacherDAO.getById(id);
        if (Objects.isNull(teacher)) {
            String message = String.format(ERROR_MESSAGE, TEACHER, id);
            throw new ServiceException(message);
        }
        return teacher;
    }

    @Override
    public List<Teacher> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return teacherDAO.getAll();
    }

    @Override
    public Page<Teacher> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Teacher> list;

        List<Teacher> teachers = teacherDAO.getAll();

        if (teachers.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, teachers.size());
            list = teachers.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), teachers.size());
    }

    @Override
    public void update(Teacher teacher) {
        log.debug(format(LOG_MESSAGE, UPDATE), teacher);

        teacherDAO.update(teacher);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        teacherDAO.delete(id);
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID), cathedraId);

        return teacherDAO.getByCathedraId(cathedraId);
    }

    @Override
    public Page<Teacher> getByCathedraId(int cathedraId, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID_PAGEABLE), cathedraId, pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Teacher> list;

        List<Teacher> teachers = teacherDAO.getByCathedraId(cathedraId);

        if (teachers.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, teachers.size());
            list = teachers.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), teachers.size());
    }
}