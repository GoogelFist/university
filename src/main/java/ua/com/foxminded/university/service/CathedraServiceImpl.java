package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class CathedraServiceImpl implements CathedraService {
    private static final String LOG_MESSAGE = "CathedraService calls cathedraDAO.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String GET_ALL_PAGEABLE = "getAll({})";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final String CATHEDRA = "cathedra";


    private final CathedraDAO cathedraDAO;

    @Autowired
    public CathedraServiceImpl(CathedraDAO cathedraDAO) {
        this.cathedraDAO = cathedraDAO;
    }

    @Override
    public void create(Cathedra cathedra) {
        log.debug(format(LOG_MESSAGE, CREATE), cathedra);

        cathedraDAO.create(cathedra);
    }

    @Override
    public Cathedra getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Cathedra cathedra = cathedraDAO.getById(id);
        if (Objects.isNull(cathedra)) {
            String message = format(ERROR_MESSAGE, CATHEDRA, id);
            throw new ServiceException(message);
        }
        return cathedra;
    }

    @Override
    public List<Cathedra> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return cathedraDAO.getAll();
    }

    @Override
    public Page<Cathedra> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Cathedra> list;

        List<Cathedra> cathedras = cathedraDAO.getAll();

        if (cathedras.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, cathedras.size());
            list = cathedras.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), cathedras.size());
    }

    @Override
    public void update(Cathedra cathedra) {
        log.debug(format(LOG_MESSAGE, UPDATE), cathedra);

        cathedraDAO.update(cathedra);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        cathedraDAO.delete(id);
    }
}