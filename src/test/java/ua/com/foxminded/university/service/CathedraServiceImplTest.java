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
import ua.com.foxminded.university.dao.CathedraDAO;
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

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CathedraServiceImplTest {
    @Mock
    private CathedraDAO mockCathedraDAO;

    private CathedraService cathedraService;
    private Cathedra cathedra;
    private List<Cathedra> cathedras;

    @BeforeEach
    void setUp() {
        cathedraService = new CathedraServiceImpl(mockCathedraDAO);
        cathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        cathedras = singletonList(cathedra);
    }

    @Test
    void shouldCallCreateCathedra() {
        cathedraService.create(cathedra);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).create(cathedra);
    }

    @Test
    void shouldCallGetByCathedraId() {
        when(mockCathedraDAO.getById(ID_1_VALUE)).thenReturn(cathedra);
        Cathedra actualCathedra = cathedraService.getById(ID_1_VALUE);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getById(ID_1_VALUE);

        assertEquals(cathedra, actualCathedra);
    }

    @Test
    void shouldCallGetAllCathedras() {
        when(mockCathedraDAO.getAll()).thenReturn(cathedras);
        List<Cathedra> actualCathedras = cathedraService.getAll();

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).getAll();
        assertEquals(cathedras, actualCathedras);
    }

    @Test
    void shouldCallGetAllCathedrasPageable() {
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
    void shouldCallUpdateCathedra() {
        cathedraService.update(cathedra);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).update(cathedra);
    }

    @Test
    void shouldCallDeleteCathedra() {
        cathedraService.delete(ID_1_VALUE);

        verify(mockCathedraDAO, times(NUMBER_OF_INVOCATIONS_VALUE)).delete(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByCathedraId() {
        String message = format(ENTITY_NOT_FOUND, CATHEDRA);
        doThrow(new ServiceException(message)).when(mockCathedraDAO).getById(ID_5_VALUE);

        Exception exception = assertThrows(ServiceException.class, () -> mockCathedraDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}