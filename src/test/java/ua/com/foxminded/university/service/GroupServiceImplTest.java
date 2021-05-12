package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        group = new Group(1, "H1-21");
        groups = singletonList(group);
    }

    @Test
    void shouldCallCreateGroup() throws DaoException {
        groupService.create(group);

        verify(mockGroupDAO, times(1)).create(group);
    }

    @Test
    void shouldCallGetGroupByID() throws DaoException {
        when(mockGroupDAO.getById(1)).thenReturn(group);
        Group actualGroup = groupService.getById(1);

        verify(mockGroupDAO, times(1)).getById(1);
        verify(mockStudentService, times(1)).getByGroupId(1);
        assertEquals(group, actualGroup);
    }

    @Test
    void shouldCallGetAllGroups() throws DaoException {
        when(mockGroupDAO.getAll()).thenReturn(groups);
        List<Group> actualGroups = groupService.getAll();

        verify(mockGroupDAO, times(1)).getAll();
        verify(mockStudentService, times(1)).getByGroupId(1);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallUpdateGroup() throws DaoException {
        groupService.update(1, group);

        verify(mockGroupDAO, times(1)).update(1, group);
    }

    @Test
    void shouldCallDeleteGroup() throws DaoException {
        groupService.delete(1);

        verify(mockGroupDAO, times(1)).delete(1);
    }

    @Test
    void shouldCallGetGroupsByCathedraId() throws DaoException {
        when(mockGroupDAO.getByCathedraId(1)).thenReturn(groups);
        List<Group> actualGroups = groupService.getByCathedraId(1);

        verify(mockGroupDAO, times(1)).getByCathedraId(1);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallAssignGroupToCathedra() throws DaoException {
        groupService.assignToCathedra(1, 1);

        verify(mockGroupDAO, times(1)).assignToCathedra(1, 1);
    }

    @Test
    void shouldCallUpdateAssignmentGroupToCathedra() throws DaoException {
        groupService.updateAssignment(1, 1);

        verify(mockGroupDAO, times(1)).getById(anyInt());
        verify(mockCathedraDAO, times(1)).getById(anyInt());
        verify(mockGroupDAO, times(1)).updateAssignment(1, 1);
    }

    @Test
    void shouldCallDeleteAssignmentGroupToCathedra() throws DaoException {
        groupService.deleteAssignment(1);

        verify(mockGroupDAO, times(1)).deleteAssignment(1);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateGroup() throws DaoException {
        doThrow(new ServiceException("Unable to create group")).when(mockGroupDAO).create(group);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.create(group));
        String actual = exception.getMessage();
        String expected = "Unable to create group";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByGroupId() throws DaoException {
        doThrow(new ServiceException("Unable to get group with ID 5")).when(mockGroupDAO).getById(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.getById(5));
        String actual = exception.getMessage();
        String expected = "Unable to get group with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllGroups() throws DaoException {
        doThrow(new ServiceException("Unable to get all groups")).when(mockGroupDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.getAll());
        String actual = exception.getMessage();
        String expected = "Unable to get all groups";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateGroup() throws DaoException {
        doThrow(new ServiceException("Unable to update group with ID 5")).when(mockGroupDAO).update(5, group);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.update(5, group));
        String actual = exception.getMessage();
        String expected = "Unable to update group with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteGroup() throws DaoException {
        doThrow(new ServiceException("Unable to delete group with ID 5")).when(mockGroupDAO).delete(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.delete(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete group with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetGroupByCathedraId() throws DaoException {
        doThrow(new ServiceException("Unable to get group by cathedraId 5")).when(mockGroupDAO).getByCathedraId(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.getByCathedraId(5));
        String actual = exception.getMessage();
        String expected = "Unable to get group by cathedraId 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantAssignToCathedra() throws DaoException {
        doThrow(new ServiceException("Unable assign group with id 5 to cathedra with id 3")).when(mockGroupDAO).assignToCathedra(3, 5);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.assignToCathedra(3, 5));
        String actual = exception.getMessage();
        String expected = "Unable assign group with id 5 to cathedra with id 3";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateAssignment() throws DaoException {
        doThrow(new ServiceException("Unable to update assignment group id 5 to cathedra with id 3")).when(mockGroupDAO).updateAssignment(3, 5);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.updateAssignment(3, 5));
        String actual = exception.getMessage();
        String expected = "Unable to update assignment group id 5 to cathedra with id 3";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteAssignment() throws DaoException {
        doThrow(new ServiceException("Unable to delete assignment group id 5 from cathedra")).when(mockGroupDAO).deleteAssignment(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockGroupDAO.deleteAssignment(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete assignment group id 5 from cathedra";

        assertEquals(expected, actual);
    }
}