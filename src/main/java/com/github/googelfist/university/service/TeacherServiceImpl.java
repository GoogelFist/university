package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Teacher;
import com.github.googelfist.university.entities.dto.TeacherDto;
import com.github.googelfist.university.entities.mapper.TeacherMapper;
import com.github.googelfist.university.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private static final String LOG_MESSAGE = "TeacherService calls teacherRepository.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String LOG_DTO_MESSAGE = "Calling teacherService.%s";
    private static final String CREATE_DTO = "createDto({})";
    private static final String GET_DTO_BY_ID = "getDtoById({})";
    private static final String GET_ALL_DTO_PAGEABLE = "getAllDto({})";
    private static final String GET_DTO_BY_CATHEDRA_ID_PAGEABLE = "getDtoByCathedraId({}, {})";
    private static final String UPDATE_DTO = "updateDto({})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String TEACHER = "teacher";


    private final TeacherRepository teacherRepository;

    @Override
    public void create(Teacher teacher) {
        log.debug(format(LOG_MESSAGE, CREATE), teacher);
        teacherRepository.save(teacher);
    }

    @Override
    public Teacher getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);
        String message = String.format(ERROR_MESSAGE, TEACHER, id);
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
    }

    @Override
    public List<Teacher> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));
        return (List<Teacher>) teacherRepository.findAll();
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
    public Page<TeacherDto> getAllDto(Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_ALL_DTO_PAGEABLE), pageable);
        Page<Teacher> teacherPage = teacherRepository.findAll(pageable);
        int totalElements = (int) teacherPage.getTotalElements();
        return new PageImpl<>(teacherPage.stream().map(TeacherMapper.INSTANCE::toTeacherDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public Page<TeacherDto> getDtoByCathedraId(int id, Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_DTO_BY_CATHEDRA_ID_PAGEABLE), id, pageable);
        Page<Teacher> teacherPage = teacherRepository.findByCathedraId(id, pageable);
        int totalElements = (int) teacherPage.getTotalElements();
        return new PageImpl<>(teacherPage.stream().map(TeacherMapper.INSTANCE::toTeacherDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public TeacherDto getDtoById(int id) {
        log.debug(format(LOG_DTO_MESSAGE, GET_DTO_BY_ID), id);
        Teacher teacher = getById(id);
        return TeacherMapper.INSTANCE.toTeacherDto(teacher);
    }

    @Override
    public void createDto(TeacherDto teacherDto) {
        log.debug(format(LOG_DTO_MESSAGE, CREATE_DTO), teacherDto);
        Teacher teacher = TeacherMapper.INSTANCE.toTeacherEntity(teacherDto);
        create(teacher);

    }

    @Override
    public void updateDto(TeacherDto teacherDto) {
        log.debug(format(LOG_DTO_MESSAGE, UPDATE_DTO), teacherDto);
        Teacher teacher = TeacherMapper.INSTANCE.toTeacherEntity(teacherDto);
        create(teacher);
    }
}