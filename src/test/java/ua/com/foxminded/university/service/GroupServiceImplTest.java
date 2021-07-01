package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.repository.GroupRepository;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class GroupServiceImplTest {
    @Mock
    private GroupRepository mockGroupRepository;

    private GroupService groupService;
    private Group group;
    private List<Group> groups;

    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(mockGroupRepository);
        group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE);
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
    void shouldCallGetAllGroupsPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        groups = new ArrayList<>();
        groups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE));
        Page<Group> expectedPageGroups = new PageImpl<>(groups, pageable, groups.size());

        when(mockGroupRepository.findAll(pageable)).thenReturn(expectedPageGroups);

        Page<Group> actualPageGroups = groupService.getAll(pageable);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedPageGroups, actualPageGroups);
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
    void shouldCallGetGroupsByCathedraId() {
        when(mockGroupRepository.findByCathedraId(ID_1_VALUE)).thenReturn(groups);
        List<Group> actualGroups = groupService.getByCathedraId(ID_1_VALUE);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByCathedraId(ID_1_VALUE);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallGetGroupsByCathedraIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        groups = new ArrayList<>();
        groups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE));
        Page<Group> expectedPageGroups = new PageImpl<>(groups, pageable, groups.size());

        when(mockGroupRepository.findByCathedraId(ID_1_VALUE, pageable)).thenReturn(expectedPageGroups);

        Page<Group> actualPageStudents = groupService.getByCathedraId(ID_1_VALUE, pageable);

        verify(mockGroupRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findByCathedraId(ID_1_VALUE, pageable);
        assertEquals(expectedPageGroups, actualPageStudents);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByGroupId() {
        String message = format(ENTITY_NOT_FOUND, GROUP);
        doThrow(new ServiceException(message)).when(mockGroupRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}