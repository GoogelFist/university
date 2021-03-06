package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Timetable;
import com.github.googelfist.university.entities.dto.TimetableDto;
import com.github.googelfist.university.entities.mapper.TimetableMapper;
import com.github.googelfist.university.repository.TimetableRepository;
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
@Transactional
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    private static final String LOG_MESSAGE = "TimetableService calls timetableRepository.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String LOG_DTO_MESSAGE = "Calling timetableService.%s";
    private static final String CREATE_DTO = "createDto({})";
    private static final String GET_DTO_BY_ID = "getDtoById({})";
    private static final String GET_ALL_DTO_PAGEABLE = "getAllDto({})";
    private static final String UPDATE_DTO = "updateDto({})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String TIMETABLE = "timetable";


    private final TimetableRepository timetableRepository;

    @Override
    public void create(Timetable timetable) {
        log.debug(format(LOG_MESSAGE, CREATE), timetable);
        timetableRepository.save(timetable);
    }

    @Override
    public Timetable getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);
        String message = String.format(ERROR_MESSAGE, TIMETABLE, id);
        return timetableRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
    }

    @Override
    public List<Timetable> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));
        return (List<Timetable>) timetableRepository.findAll();
    }

    @Override
    public void update(Timetable timetable) {
        log.debug(format(LOG_MESSAGE, UPDATE), timetable);
        timetableRepository.save(timetable);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);
        timetableRepository.deleteById(id);
    }

    @Override
    public Page<TimetableDto> getAllDto(Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_ALL_DTO_PAGEABLE), pageable);
        Page<Timetable> timetablePage = timetableRepository.findAll(pageable);
        int totalElements = (int) timetablePage.getTotalElements();
        return new PageImpl<>(timetablePage.stream().map(TimetableMapper.INSTANCE::toTimetableDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public TimetableDto getDtoById(int id) {
        log.debug(format(LOG_DTO_MESSAGE, GET_DTO_BY_ID), id);
        Timetable timetable = getById(id);
        return TimetableMapper.INSTANCE.toTimetableDto(timetable);
    }

    @Override
    public void createDto(TimetableDto timetableDto) {
        log.debug(format(LOG_DTO_MESSAGE, CREATE_DTO), timetableDto);
        Timetable timetable = TimetableMapper.INSTANCE.toTimetableEntity(timetableDto);
        create(timetable);
    }

    @Override
    public void updateDto(TimetableDto timetableDto) {
        log.debug(format(LOG_DTO_MESSAGE, UPDATE_DTO), timetableDto);
        Timetable timetable = TimetableMapper.INSTANCE.toTimetableEntity(timetableDto);
        update(timetable);
    }
}