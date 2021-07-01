package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.repository.TeacherRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

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

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void create(Teacher teacher) {
        log.debug(format(LOG_MESSAGE, CREATE), teacher);

        teacherRepository.save(teacher);
    }

    @Override
    public Teacher getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (!optionalTeacher.isPresent()) {
            String message = String.format(ERROR_MESSAGE, TEACHER, id);
            throw new ServiceException(message);
        }
        return optionalTeacher.get();
    }

    @Override
    public List<Teacher> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return (List<Teacher>) teacherRepository.findAll();
    }

    @Override
    public Page<Teacher> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        return teacherRepository.findAll(pageable);
    }

    @Override
    public void update(Teacher teacher) {
        log.debug(format(LOG_MESSAGE, UPDATE), teacher);

        teacherRepository.save(teacher);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        teacherRepository.deleteById(id);
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID), cathedraId);

        return teacherRepository.findByCathedraId(cathedraId);
    }

    @Override
    public Page<Teacher> getByCathedraId(int cathedraId, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID_PAGEABLE), cathedraId, pageable);

        return teacherRepository.findByCathedraId(cathedraId, pageable);
    }
}