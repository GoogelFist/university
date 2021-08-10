package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.entities.dto.GroupDto;
import com.github.googelfist.university.entities.mapper.GroupMapper;
import com.github.googelfist.university.repository.GroupRepository;
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
public class GroupServiceImpl implements GroupService {
    private static final String LOG_MESSAGE = "GroupService calls groupRepository.%s";
    private static final String CREATE = "create({})";
    private static final String GET_BY_ID = "getById({})";
    private static final String GET_ALL = "getAll()";
    private static final String UPDATE = "update({})";
    private static final String DELETE = "delete(Id {})";

    private static final String LOG_DTO_MESSAGE = "Calling teacherService.%s";
    private static final String CREATE_DTO = "createDto({})";
    private static final String GET_DTO_BY_ID = "getDtoById({})";
    private static final String GET_ALL_DTO_PAGEABLE = "getAllDto({})";
    private static final String GET_BY_CATHEDRA_DTO_PAGEABLE = "getDtoByCathedraId({}, {})";
    private static final String UPDATE_DTO = "updateDto({})";

    private static final String ERROR_MESSAGE = "Entity %s with id %s not found";
    private static final Object GROUP = "group";


    private final GroupRepository groupRepository;

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
    public Page<GroupDto> getAllDto(Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_ALL_DTO_PAGEABLE), pageable);
        Page<Group> groupPage = groupRepository.findAll(pageable);
        int totalElements = (int) groupPage.getTotalElements();
        return new PageImpl<>(groupPage.stream().map(GroupMapper.INSTANCE::toGroupDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public Page<GroupDto> getDtoByCathedraId(int id, Pageable pageable) {
        log.debug(format(LOG_DTO_MESSAGE, GET_BY_CATHEDRA_DTO_PAGEABLE), id, pageable);
        Page<Group> groupPage = groupRepository.findByCathedraId(id, pageable);
        int totalElements = (int) groupPage.getTotalElements();
        return new PageImpl<>(groupPage.stream().map(GroupMapper.INSTANCE::toGroupDto)
            .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public GroupDto getDtoById(int id) {
        log.debug(format(LOG_DTO_MESSAGE, GET_DTO_BY_ID), id);
        Group group = getById(id);
        return GroupMapper.INSTANCE.toGroupDto(group);
    }

    @Override
    public void createDto(GroupDto groupDto) {
        log.debug(format(LOG_DTO_MESSAGE, CREATE_DTO), groupDto);
        Group group = GroupMapper.INSTANCE.toGroupEntity(groupDto);
        create(group);
    }

    @Override
    public void updateDto(GroupDto groupDto) {
        log.debug(format(LOG_DTO_MESSAGE, UPDATE_DTO), groupDto);
        Group group = GroupMapper.INSTANCE.toGroupEntity(groupDto);
        create(group);
    }
}