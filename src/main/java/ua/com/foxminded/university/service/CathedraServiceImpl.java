package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.repository.CathedraRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class CathedraServiceImpl implements CathedraService {
    private static final String LOG_MESSAGE = "CathedraService calls cathedraRepository.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String GET_ALL_PAGEABLE = "getAll({})";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String CATHEDRA = "cathedra";


    private final CathedraRepository cathedraRepository;

    @Autowired
    public CathedraServiceImpl(CathedraRepository cathedraRepository) {
        this.cathedraRepository = cathedraRepository;
    }

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
    public Page<Cathedra> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        return cathedraRepository.findAll(pageable);
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
}