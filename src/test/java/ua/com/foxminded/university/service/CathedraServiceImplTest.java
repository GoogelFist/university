package ua.com.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldCallCreateCathedra() throws DaoException {
        cathedraService.create(cathedra);

        verify(mockCathedraDAO, times(1)).create(cathedra);
    }

    @Test
    void shouldCallGetByCathedraId() throws DaoException {
        when(mockCathedraDAO.getById(1)).thenReturn(cathedra);
        Cathedra actualCathedra = cathedraService.getById(1);

        verify(mockCathedraDAO, times(1)).getById(1);
        verify(mockTeacherService, times(1)).getByCathedraId(1);
        verify(mockGroupService, times(1)).getByCathedraId(1);
        assertEquals(cathedra, actualCathedra);
    }

    @Test
    void shouldCallGetAllCathedras() throws DaoException {
        when(mockCathedraDAO.getAll()).thenReturn(cathedras);
        List<Cathedra> actualCathedras = cathedraService.getAll();

        verify(mockCathedraDAO, times(1)).getAll();
        verify(mockGroupService, times(1)).getByCathedraId(1);
        verify(mockTeacherService, times(1)).getByCathedraId(1);
        assertEquals(cathedras, actualCathedras);
    }


    @Test
    void shouldCallUpdateCathedra() throws DaoException {
        cathedraService.update(1, cathedra);

        verify(mockCathedraDAO, times(1)).update(1, cathedra);
    }

    @Test
    void shouldCallDeleteCathedra() throws DaoException {
        cathedraService.delete(1);

        verify(mockCathedraDAO, times(1)).delete(1);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateCathedra() throws DaoException {
        doThrow(new ServiceException("Unable to create cathedra")).when(mockCathedraDAO).create(cathedra);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.create(cathedra));
        String actual = exception.getMessage();
        String expected = "Unable to create cathedra";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByCathedraId() throws DaoException {
        doThrow(new ServiceException("Unable to get cathedra with ID 5")).when(mockCathedraDAO).getById(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.getById(5));
        String actual = exception.getMessage();
        String expected = "Unable to get cathedra with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllCathedras() throws DaoException {
        doThrow(new ServiceException("Unable to get all cathedras")).when(mockCathedraDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.getAll());
        String actual = exception.getMessage();
        String expected = "Unable to get all cathedras";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateCathedra() throws DaoException {
        doThrow(new ServiceException("Unable to update cathedra with ID 5")).when(mockCathedraDAO).update(5, cathedra);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.update(5, cathedra));
        String actual = exception.getMessage();
        String expected = "Unable to update cathedra with ID 5";

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteCathedra() throws DaoException {
        doThrow(new ServiceException("Unable to delete cathedra with ID 5")).when(mockCathedraDAO).delete(5);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.delete(5));
        String actual = exception.getMessage();
        String expected = "Unable to delete cathedra with ID 5";

        assertEquals(expected, actual);
    }
}