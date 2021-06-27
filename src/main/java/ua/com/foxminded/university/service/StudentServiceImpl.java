package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private static final String LOG_MESSAGE = "StudentService calls studentDAO.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String GET_ALL_PAGEABLE = "getAll({})";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";
    private static final String GET_BY_GROUP_ID = "getByGroupId({})";
    private static final String GET_BY_GROUP_ID_PAGEABLE = "getByGroupId({}, {})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String STUDENT = "student";


    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public void create(Student student) {
        log.debug(format(LOG_MESSAGE, CREATE), student);

        studentDAO.create(student);
    }

    @Override
    public Student getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Student student = studentDAO.getById(id);
        if (Objects.isNull(student)) {
            String message = format(ERROR_MESSAGE, STUDENT, id);
            throw new ServiceException(message);
        }
        return student;
    }

    @Override
    public List<Student> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return studentDAO.getAll();
    }

    @Override
    public Page<Student> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Student> list;

        List<Student> students = studentDAO.getAll();

        if (students.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, students.size());
            list = students.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), students.size());
    }

    @Override
    public void update(Student student) {
        log.debug(format(LOG_MESSAGE, UPDATE), student);

        studentDAO.update(student);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        studentDAO.delete(id);
    }

    @Override
    public List<Student> getByGroupId(int groupId) {
        log.debug(format(LOG_MESSAGE, GET_BY_GROUP_ID), groupId);

        return studentDAO.getByGroupId(groupId);
    }

    @Override
    public Page<Student> getByGroupId(int groupId, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_GROUP_ID_PAGEABLE), groupId, pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Student> list;

        List<Student> students = studentDAO.getByGroupId(groupId);

        if (students.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, students.size());
            list = students.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), students.size());
    }
}