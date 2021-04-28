package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.entities.Group;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class GroupServiceImplTest {
    @Mock
    private GroupDAO mockGroupDAO;

    @Mock
    private StudentService mockStudentService;

    private GroupService groupService;
    private Group group;
    private List<Group> groups;

    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(mockGroupDAO, mockStudentService);
        group = new Group(1, "H1-21");
        groups = singletonList(group);
    }

    @Test
    void shouldCallCreateGroup() {
        groupService.create(group);

        verify(mockGroupDAO, times(1)).create(group);
    }

    @Test
    void shouldCallGetGroupByID() {
        when(mockGroupDAO.getById(1)).thenReturn(group);
        Group actualGroup = groupService.getById(1);

        verify(mockGroupDAO, times(1)).getById(1);
        verify(mockStudentService, times(1)).getByGroupId(1);
        assertEquals(group, actualGroup);
    }

    @Test
    void shouldCallGetAllGroups() {
        when(mockGroupDAO.getAll()).thenReturn(groups);
        List<Group> actualGroups = groupService.getAll();

        verify(mockGroupDAO, times(1)).getAll();
        verify(mockStudentService, times(1)).getByGroupId(1);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallUpdateGroup() {
        groupService.update(1, group);

        verify(mockGroupDAO, times(1)).update(1, group);
    }

    @Test
    void shouldCallDeleteGroup() {
        groupService.delete(1);

        verify(mockGroupDAO, times(1)).delete(1);
    }

    @Test
    void shouldCallGetGroupsByCathedraId() {
        when(mockGroupDAO.getByCathedraId(1)).thenReturn(groups);
        List<Group> actualGroups = groupService.getByCathedraId(1);

        verify(mockGroupDAO, times(1)).getByCathedraId(1);
        assertEquals(groups, actualGroups);
    }

    @Test
    void shouldCallAssignGroupToCathedra() {
        groupService.assignToCathedra(1, 1);

        verify(mockGroupDAO, times(1)).assignToCathedra(1, 1);
    }

    @Test
    void shouldCallUpdateAssignmentGroupToCathedra() {
        groupService.updateAssignment(1, 1);

        verify(mockGroupDAO, times(1)).updateAssignment(1, 1);
    }

    @Test
    void shouldCallDeleteAssignmentGroupToCathedra() {
        groupService.deleteAssignment(1);

        verify(mockGroupDAO, times(1)).deleteAssignment(1);
    }
}