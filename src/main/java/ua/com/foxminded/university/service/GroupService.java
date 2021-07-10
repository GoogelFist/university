package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.dto.GroupDto;

public interface GroupService extends GenericServiceJpa<Group> {
    Page<GroupDto> getAllDto(Pageable pageable);

    Page<GroupDto> getDtoByCathedraId(int id, Pageable pageable);

    GroupDto getDtoById(int id);

    void createDto(GroupDto groupDto);

    void updateDto(GroupDto groupDto);
}