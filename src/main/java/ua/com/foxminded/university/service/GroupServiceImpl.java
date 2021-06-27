package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    private static final String LOG_MESSAGE = "GroupService calls groupDAO.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String GET_ALL_PAGEABLE = "getAll({})";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";
    private static final String GET_BY_CATHEDRA_ID = "getByCathedraId({})";
    private static final String GET_BY_CATHEDRA_ID_PAGEABLE = "getByCathedraId({}, {})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final Object GROUP = "group";


    private final GroupDAO groupDAO;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public void create(Group group) {
        log.debug(format(LOG_MESSAGE, CREATE), group);

        groupDAO.create(group);
    }

    @Override
    public Group getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        Group group = groupDAO.getById(id);
        if (Objects.isNull(group)) {
            String message = String.format(ERROR_MESSAGE, GROUP, id);
            throw new ServiceException(message);
        }
        return group;
    }

    @Override
    public List<Group> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return groupDAO.getAll();
    }

    @Override
    public Page<Group> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Group> list;

        List<Group> groups = groupDAO.getAll();

        if (groups.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, groups.size());
            list = groups.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), groups.size());
    }

    @Override
    public void update(Group group) {
        log.debug(format(LOG_MESSAGE, UPDATE), group);

        groupDAO.update(group);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        groupDAO.delete(id);
    }

    @Override
    public List<Group> getByCathedraId(int cathedraId) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID), cathedraId);

        return groupDAO.getByCathedraId(cathedraId);
    }

    @Override
    public Page<Group> getByCathedraId(int cathedraId, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID_PAGEABLE), cathedraId, pageable);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Group> list;

        List<Group> groups = groupDAO.getByCathedraId(cathedraId);

        if (groups.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, groups.size());
            list = groups.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), groups.size());
    }
}