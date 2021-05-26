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
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Cathedra;
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

    @Mock
    private StudentService mockStudentService;

    @Mock
    private CathedraDAO mockCathedraDAO;

    private GroupService groupService;
    private Group group;
    private List<Group> groups;

    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(mockGroupDAO, mockStudentService, mockCathedraDAO);
        group = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE));
        groups = singletonList(group);
    }

    @Test
    void shouldCallCreateGroup() throws DaoException {
        groupService.create(group);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(group);
    }

    @Test
    void shouldCallGetGroupByID() throws DaoException {
        when(mockGroupDAO.getById(ID_1_VALUE)).thenReturn(group);
        Group actualGroup = groupService.getById(ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockStudentService, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        assertEquals(group, actualGroup);
    }

    @Test
    void shouldCallGetAllGroups() throws DaoException {
        when(mockGroupDAO.getAll()).thenReturn(groups);
        List<Group> actualGroups = groupService.getAll();

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        verify(mockStudentService, times(NUMBER_OF_INVOCATIONS_VALUE)).getByGroupId(ID_1_VALUE);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallGetAllGroupsPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        groups = new ArrayList<>();
        groups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE)));
        when(mockGroupDAO.getAll()).thenReturn(groups);

        Page<Group> expectedPageGroups = new PageImpl<>(groups, pageable, groups.size());
        Page<Group> actualPageGroups = groupService.getAll(pageable);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageGroups, actualPageGroups);
    }

    @Test
    void shouldCallUpdateGroup() throws DaoException {
        groupService.update(ID_1_VALUE, group);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(ID_1_VALUE, group);
    }

    @Test
    void shouldCallDeleteGroup() throws DaoException {
        groupService.delete(ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldCallGetGroupsByCathedraId() throws DaoException {
        when(mockGroupDAO.getByCathedraId(ID_1_VALUE)).thenReturn(groups);
        List<Group> actualGroups = groupService.getByCathedraId(ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallGetGroupsByCathedraIdPageable() throws DaoException {
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        groups = new ArrayList<>();
        groups.add(new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE)));
        when(mockGroupDAO.getByCathedraId(ID_1_VALUE)).thenReturn(groups);

        Page<Group> expectedPageStudents = new PageImpl<>(groups, pageable, groups.size());
        Page<Group> actualPageStudents = groupService.getByCathedraId(ID_1_VALUE, pageable);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getByCathedraId(ID_1_VALUE);
        assertEquals(expectedPageStudents, actualPageStudents);
    }

    @Test
    void shouldCallAssignGroupToCathedra() throws DaoException {
        groupService.assignToCathedra(ID_1_VALUE, ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).assignToCathedra(ID_1_VALUE, ID_1_VALUE);
    }

    @Test
    void shouldCallUpdateAssignmentGroupToCathedra() throws DaoException {
        groupService.updateAssignment(ID_1_VALUE, ID_1_VALUE);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);
        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).updateAssignment(ID_1_VALUE, ID_1_VALUE);
    }

    @Test
    void shouldCallDeleteAssignmentGroupToCathedra() throws DaoException {
        groupService.deleteAssignment(ID_1_VALUE);

        verify(mockGroupDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteAssignment(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateGroup() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_CREATE, GROUP);
        doThrow(new ServiceException(message)).when(mockGroupDAO).create(group);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.create(group));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByGroupId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, GET, GROUP, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockGroupDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllGroups() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_GET_ALL, GROUP);
        doThrow(new ServiceException(message)).when(mockGroupDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.getAll());
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateGroup() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, UPDATE, GROUP, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockGroupDAO).update(ID_5_VALUE, group);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.update(ID_5_VALUE, group));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteGroup() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, DELETE, GROUP, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockGroupDAO).delete(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.delete(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetGroupByCathedraId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ENTITY_ID, UPDATE, GROUP, CATHEDRA, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockGroupDAO).getByCathedraId(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.getByCathedraId(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantAssignToCathedra() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGN, ASSIGN, GROUP, ID_5_VALUE, CATHEDRA, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockGroupDAO).assignToCathedra(ID_3_VALUE, ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.assignToCathedra(ID_3_VALUE, ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateAssignment() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGNMENT, UPDATE, GROUP, ID_5_VALUE, CATHEDRA, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockGroupDAO).updateAssignment(ID_3_VALUE, ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.updateAssignment(ID_3_VALUE, ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteAssignment() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_ASSIGNMENT, DELETE, GROUP, ID_5_VALUE, CATHEDRA, ID_3_VALUE);
        doThrow(new ServiceException(message)).when(mockGroupDAO).deleteAssignment(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.deleteAssignment(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}