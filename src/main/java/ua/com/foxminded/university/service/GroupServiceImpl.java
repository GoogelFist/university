package ua.com.foxminded.university.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.repository.GroupRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    private static final String LOG_MESSAGE = "GroupService calls groupRepository.%s";
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


    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void create(Group group) {
        log.debug(format(LOG_MESSAGE, CREATE), group);

        groupRepository.save(group);
    }

    @Override
    public Group getById(int id) {
        log.debug(format(LOG_MESSAGE, GET_BY_ID), id);

        String message = String.format(ERROR_MESSAGE, GROUP, id);
        return groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
    }

    @Override
    public List<Group> getAll() {
        log.debug(format(LOG_MESSAGE, GET_ALL));

        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public Page<Group> getAll(Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_ALL_PAGEABLE), pageable);

        return groupRepository.findAll(pageable);
    }

    @Override
    public void update(Group group) {
        log.debug(format(LOG_MESSAGE, UPDATE), group);

        groupRepository.save(group);
    }

    @Override
    public void delete(int id) {
        log.debug(format(LOG_MESSAGE, DELETE), id);

        groupRepository.deleteById(id);
    }

    @Override
    public List<Group> getByCathedraId(int cathedraId) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID), cathedraId);

        return groupRepository.findByCathedraId(cathedraId);
    }

    @Override
    public Page<Group> getByCathedraId(int cathedraId, Pageable pageable) {
        log.debug(format(LOG_MESSAGE, GET_BY_CATHEDRA_ID_PAGEABLE), cathedraId, pageable);

        return groupRepository.findByCathedraId(cathedraId, pageable);
    }
}