package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.entities.dto.GroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService extends GenericServiceJpa<Group> {
    Page<GroupDto> getAllDto(Pageable pageable);

    Page<GroupDto> getDtoByCathedraId(int id, Pageable pageable);

    GroupDto getDtoById(int id);

    void createDto(GroupDto groupDto);

    void updateDto(GroupDto groupDto);
}