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
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Cathedra;
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
        cathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        cathedras = singletonList(cathedra);
    }

    @Test
    void shouldCallCreateCathedra() throws DaoException {
        cathedraService.create(cathedra);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(cathedra);
    }

    @Test
    void shouldCallGetByCathedraId() throws DaoException {
        when(mockCathedraDAO.getById(ID_1_VALUE)).thenReturn(cathedra);
        Cathedra actualCathedra = cathedraService.getById(ID_1_VALUE);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);

        assertEquals(cathedra, actualCathedra);
    }

    @Test
    void shouldCallGetAllCathedras() throws DaoException {
        when(mockCathedraDAO.getAll()).thenReturn(cathedras);
        List<Cathedra> actualCathedras = cathedraService.getAll();

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(cathedras, actualCathedras);
    }

    @Test
    void shouldCallGetAllCathedrasPageable() throws DaoException {
        cathedras = new ArrayList<>();
        cathedras.add(new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE));
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        when(mockCathedraDAO.getAll()).thenReturn(cathedras);

        Page<Cathedra> expectedPageStudents = new PageImpl<>(cathedras, pageable, cathedras.size());
        Page<Cathedra> actualPageStudents = cathedraService.getAll(pageable);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(expectedPageStudents, actualPageStudents);
    }


    @Test
    void shouldCallUpdateCathedra() throws DaoException {
        cathedraService.update(ID_1_VALUE, cathedra);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(ID_1_VALUE, cathedra);
    }

    @Test
    void shouldCallDeleteCathedra() throws DaoException {
        cathedraService.delete(ID_1_VALUE);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantCreateCathedra() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_CREATE, CATHEDRA);
        doThrow(new ServiceException(message)).when(mockCathedraDAO).create(cathedra);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.create(cathedra));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByCathedraId() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, GET, CATHEDRA, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockCathedraDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetAllCathedras() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_GET_ALL, CATHEDRAS);
        doThrow(new ServiceException(message)).when(mockCathedraDAO).getAll();

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.getAll());
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantUpdateCathedra() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, UPDATE, CATHEDRA, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockCathedraDAO).update(ID_5_VALUE, cathedra);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.update(ID_5_VALUE, cathedra));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantDeleteCathedra() throws DaoException {
        String message = format(SERVICE_EXCEPTION_MESSAGE_BY_ID, DELETE, CATHEDRA, ID_5_VALUE);
        doThrow(new ServiceException(message)).when(mockCathedraDAO).delete(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.delete(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}