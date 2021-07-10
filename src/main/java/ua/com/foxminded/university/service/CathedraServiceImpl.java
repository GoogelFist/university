package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.dto.CathedraDto;
import ua.com.foxminded.university.entities.mapper.CathedraMapper;
import ua.com.foxminded.university.repository.CathedraRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CathedraServiceImpl implements CathedraService {
    private static final String LOG_MESSAGE = "CathedraService calls cathedraRepository.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String LOG_DTO_MESSAGE = "Calling cathedraService.%s";
    private static final String CREATE_DTO = "createDto({})";
    private static final String GET_DTO_BY_ID = "getDtoById({})";
    private static final String GET_ALL_DTO_PAGEABLE = "getAllDto({})";
    private static final String UPDATE_DTO = "updateDto({})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String CATHEDRA = "cathedra";


    private final CathedraRepository cathedraRepository;

    @Override
    public void create(Cathedra cathedra) {
        log.debug(format(LOG_MESSAGE, CREATE), cathedra);
        cathedraRepository.save(cathedra);
    }

    @Override
    public Cathedra getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);
        String message = format(ERROR_MESSAGE, CATHEDRA, id);
        return cathedraRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
    }

    @Override
    public List<Cathedra> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));
        return (List<Cathedra>) cathedraRepository.findAll();
    }

    @Override
    public void update(Cathedra cathedra) {
        log.debug(format(LOG_MESSAGE, UPDATE), cathedra);
        cathedraRepository.save(cathedra);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);
        cathedraRepository.deleteById(id);
    }

    @Override
    public Page<CathedraDto> getAllDto(Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_ALL_DTO_PAGEABLE), pageable);
        Page<Cathedra> cathedraPage = cathedraRepository.findAll(pageable);
        int totalElements = (int) cathedraPage.getTotalElements();
        return new PageImpl<>(cathedraPage.stream().map(CathedraMapper.INSTANCE::toCathedraDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public CathedraDto getDtoById(int id) {
        log.debug(format(LOG_DTO_MESSAGE, GET_DTO_BY_ID), id);
        Cathedra cathedra = getById(id);
        return CathedraMapper.INSTANCE.toCathedraDto(cathedra);
    }

    @Override
    public void createDto(CathedraDto cathedraDto) {
        log.debug(format(LOG_DTO_MESSAGE, CREATE_DTO), cathedraDto);
        Cathedra cathedra = CathedraMapper.INSTANCE.toCathedraEntity(cathedraDto);
        create(cathedra);
    }

    @Override
    public void updateDto(CathedraDto cathedraDto) {
        log.debug(format(LOG_DTO_MESSAGE, UPDATE_DTO), cathedraDto);
        Cathedra cathedra = CathedraMapper.INSTANCE.toCathedraEntity(cathedraDto);
        update(cathedra);
    }
}