package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.repository.StudentRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

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


    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void create(Student student) {
        log.debug(format(LOG_MESSAGE, CREATE), student);

        studentRepository.save(student);
    }

    @Override
    public Student getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            String message = format(ERROR_MESSAGE, STUDENT, id);
            throw new ServiceException(message);
        }
        return optionalStudent.get();
    }

    @Override
    public List<Student> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public Page<Student> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        return studentRepository.findAll(pageable);
    }

    @Override
    public void update(Student student) {
        log.debug(format(LOG_MESSAGE, UPDATE), student);

        studentRepository.save(student);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getByGroupId(int groupId) {
        log.debug(format(LOG_MESSAGE, GET_BY_GROUP_ID), groupId);

        return studentRepository.findByGroupId(groupId);
    }

    @Override
    public Page<Student> getByGroupId(int groupId, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_GROUP_ID_PAGEABLE), groupId, pageable);

        return studentRepository.findByGroupId(groupId, pageable);
    }
}