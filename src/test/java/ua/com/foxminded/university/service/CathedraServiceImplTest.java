package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.entities.Cathedra;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
class CathedraServiceImplTest {
    @Mock
    private CathedraDAO mockCathedraDAO;

    @Mock
    private GroupService mockGroupService;

    @Mock
    private TeacherService mockTeacherService;

    private CathedraService cathedraService;
    private Cathedra cathedra;
    private List<Cathedra> cathedras;

    @BeforeEach
    void setUp() {
        cathedraService = new CathedraServiceImpl(mockCathedraDAO, mockGroupService, mockTeacherService);
        cathedra = new Cathedra(1, "physics");
        cathedras = singletonList(cathedra);
    }

    @Test
    void shouldCallCreateCathedra() {
        cathedraService.create(cathedra);

        verify(mockCathedraDAO, times(1)).create(cathedra);
    }

    @Test
    void shouldCallGetByCathedraId() {
        when(mockCathedraDAO.getById(1)).thenReturn(cathedra);
        Cathedra actualCathedra = cathedraService.getById(1);

        verify(mockCathedraDAO, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getByCathedraId(1);
        verify(mockGroupService, times(1)).getByCathedraId(1);
        assertEquals(cathedra, actualCathedra);
    }

    @Test
    void shouldCallGetAllCathedras() {
        when(mockCathedraDAO.getAll()).thenReturn(cathedras);
        List<Cathedra> actualCathedras = cathedraService.getAll();

        verify(mockCathedraDAO, times(1)).getAll();
        verify(mockGroupService, times(1)).getByCathedraId(1);
        verify(mockTeacherService, times(1)).getByCathedraId(1);
        assertEquals(cathedras, actualCathedras);
    }

    @Test
    void shouldCallUpdateCathedra() {
        cathedraService.update(1, cathedra);

        verify(mockCathedraDAO, times(1)).update(1, cathedra);
    }

    @Test
    void shouldCallDeleteCathedra() {
        cathedraService.delete(1);

        verify(mockCathedraDAO, times(1)).delete(1);
    }
}