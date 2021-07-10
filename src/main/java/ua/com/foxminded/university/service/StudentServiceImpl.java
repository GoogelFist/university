package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.dto.StudentDto;
import ua.com.foxminded.university.entities.mapper.StudentMapper;
import ua.com.foxminded.university.repository.StudentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private static final String LOG_MESSAGE = "StudentService calls studentRepository.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String LOG_DTO_MESSAGE = "Calling teacherService.%s";
    private static final String CREATE_DTO = "createDto({})";
    private static final String GET_DTO_BY_ID = "getDtoById({})";
    private static final String GET_ALL_DTO_PAGEABLE = "getAllDto({})";
    private static final String GET_DTO_BY_GROUP_ID_PAGEABLE = "getDtoByGroupId({}, {})";
    private static final String UPDATE_DTO = "updateDto({})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String STUDENT = "student";


    private final StudentRepository studentRepository;

    @Override
    public void create(Student student) {
        log.debug(format(LOG_MESSAGE, CREATE), student);

        studentRepository.save(student);
    }

    @Override
    public Student getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        String message = format(ERROR_MESSAGE, STUDENT, id);
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
    }

    @Override
    public List<Student> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return (List<Student>) studentRepository.findAll();
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
    public Page<StudentDto> getAllDto(Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_ALL_DTO_PAGEABLE), pageable);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        int totalElements = (int) studentPage.getTotalElements();
        return new PageImpl<>(studentPage.stream().map(StudentMapper.INSTANCE::toStudentDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public Page<StudentDto> getDtoByGroupId(int id, Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_DTO_BY_GROUP_ID_PAGEABLE), id, pageable);
        Page<Student> studentPage = studentRepository.findByGroupId(id, pageable);
        int totalElements = (int) studentPage.getTotalElements();
        return new PageImpl<>(studentPage.stream().map(StudentMapper.INSTANCE::toStudentDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public StudentDto getDtoById(int id) {
        log.debug(format(LOG_DTO_MESSAGE, GET_DTO_BY_ID), id);
        Student student = getById(id);
        return StudentMapper.INSTANCE.toStudentDto(student);
    }

    @Override
    public void createDto(StudentDto studentDto) {
        log.debug(format(LOG_DTO_MESSAGE, CREATE_DTO), studentDto);
        Student student = StudentMapper.INSTANCE.toStudentEntity(studentDto);
        create(student);
    }

    @Override
    public void updateDto(StudentDto studentDto) {
        log.debug(format(LOG_DTO_MESSAGE, UPDATE_DTO), studentDto);
        Student student = StudentMapper.INSTANCE.toStudentEntity(studentDto);
        create(student);
    }
}