package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.dto.GroupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import com.github.googelfist.university.entities.Cathedra;
import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.repository.GroupRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static com.github.googelfist.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class GroupServiceImplTest {
    @Mock
    private GroupRepository mockGroupRepository;

    private GroupService groupService;
    private Group group;
    private GroupDto groupDto;
    private List<Group> groups;

    @BeforeEach
    void setUp() {
        Cathedra cathedra = new Cathedra();
        cathedra.setId(ID_1_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        groupService = new GroupServiceImpl(mockGroupRepository);

        group = new Group();
        group.setName(GROUP_1_NAME_VALUE);
        group.setCathedra(cathedra);

        groupDto = new GroupDto();
        groupDto.setName(GROUP_1_NAME_VALUE);
        groupDto.setCathedraId(ID_1_VALUE);
        groupDto.setCathedraName(CATHEDRA_1_NAME_VALUE);

        groups = singletonList(group);
    }

    @Test
    void shouldCallCreateGroup() {
        groupService.create(group);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(group);
    }

    @Test
    void shouldCallGetGroupByID() {
        when(mockGroupRepository.findById(ID_1_VALUE)).thenReturn(java.util.Optional.ofNullable(group));

        Group actualGroup = groupService.getById(ID_1_VALUE);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(group, actualGroup);
    }

    @Test
    void shouldCallGetAllGroups() {
        when(mockGroupRepository.findAll()).thenReturn(groups);
        List<Group> actualGroups = groupService.getAll();

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallUpdateGroup() {
        groupService.update(group);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(group);
    }

    @Test
    void shouldCallDeleteGroup() {
        groupService.delete(ID_1_VALUE);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldCallGetAllDtoGroupsPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Group> groupPage = new PageImpl<>(groups, pageable, groups.size());
        when(mockGroupRepository.findAll(pageable)).thenReturn(groupPage);

        List<GroupDto> groupsDto = new ArrayList<>();
        groupsDto.add(groupDto);
        Page<GroupDto> expectedGroupsDtoPage = new PageImpl<>(groupsDto, pageable, groupsDto.size());

        Page<GroupDto> actualGroupsDtoPage = groupService.getAllDto(pageable);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedGroupsDtoPage, actualGroupsDtoPage);
    }

    @Test
    void shouldCallGetAllDtoGroupsByCathedraIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Group> groupPage = new PageImpl<>(groups, pageable, groups.size());
        when(mockGroupRepository.findByCathedraId(ID_1_VALUE, pageable)).thenReturn(groupPage);

        List<GroupDto> groupsDto = new ArrayList<>();
        groupsDto.add(groupDto);
        Page<GroupDto> expectedGroupsDtoPage = new PageImpl<>(groupsDto, pageable, groupsDto.size());

        Page<GroupDto> actualGroupsDtoPage = groupService.getDtoByCathedraId(ID_1_VALUE, pageable);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByCathedraId(ID_1_VALUE, pageable);
        assertEquals(expectedGroupsDtoPage, actualGroupsDtoPage);
    }

    @Test
    void shouldCallGetDtoGroupById() {
        when(mockGroupRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(group));
        GroupDto actualGroupDto = groupService.getDtoById(ID_1_VALUE);
        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);

        assertEquals(groupDto, actualGroupDto);
    }

    @Test
    void shouldCallCreateDtoGroup() {
        groupService.createDto(groupDto);
        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(group);
    }

    @Test
    void shouldCallUpdateDtoGroup() {
        groupService.updateDto(groupDto);
        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(group);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCantGetByGroupId() {
        String message = format(ENTITY_NOT_FOUND, GROUP);
        doThrow(new EntityNotFoundException(message)).when(mockGroupRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> mockGroupRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}