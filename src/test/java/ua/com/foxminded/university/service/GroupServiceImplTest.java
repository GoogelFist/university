package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class GroupServiceImplTest {
    @Mock
    private GroupDAO mockGroupDAO;

    private GroupService groupService;
    private Group group;
    private List<Group> groups;

    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(mockGroupDAO);
        group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE);
        groups = singletonList(group);
    }

    @Test
    void shouldCallCreateGroup() {
        groupService.create(group);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(group);
    }

    @Test
    void shouldCallGetGroupByID() {
        when(mockGroupDAO.getById(ID_1_VALUE)).thenReturn(group);
        Group actualGroup = groupService.getById(ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        assertEquals(group, actualGroup);
    }

    @Test
    void shouldCallGetAllGroups() {
        when(mockGroupDAO.getAll()).thenReturn(groups);
        List<Group> actualGroups = groupService.getAll();

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallGetAllGroupsPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        groups = new ArrayList<>();
        groups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE));
        when(mockGroupDAO.getAll()).thenReturn(groups);

        Page<Group> expectedPageGroups = new PageImpl<>(groups, pageable, groups.size());
        Page<Group> actualPageGroups = groupService.getAll(pageable);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageGroups, actualPageGroups);
    }

    @Test
    void shouldCallUpdateGroup() {
        groupService.update(group);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(group);
    }

    @Test
    void shouldCallDeleteGroup() {
        groupService.delete(ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetGroupsByCathedraId() {
        when(mockGroupDAO.getByCathedraId(ID_1_VALUE)).thenReturn(groups);
        List<Group> actualGroups = groupService.getByCathedraId(ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallGetGroupsByCathedraIdPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        groups = new ArrayList<>();
        groups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE));
        when(mockGroupDAO.getByCathedraId(ID_1_VALUE)).thenReturn(groups);

        Page<Group> expectedPageStudents = new PageImpl<>(groups, pageable, groups.size());
        Page<Group> actualPageStudents = groupService.getByCathedraId(ID_1_VALUE, pageable);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByGroupId() {
        String message = format(ENTITY_NOT_FOUND, GROUP);
        doThrow(new ServiceException(message)).when(mockGroupDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}