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
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.repository.CathedraRepository;

import javax.persistence.EntityNotFoundException;
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
    private CathedraRepository mockCathedraRepository;

    private CathedraService cathedraService;
    private Cathedra cathedra;
    private List<Cathedra> cathedras;

    @BeforeEach
    void setUp() {
        cathedraService = new CathedraServiceImpl(mockCathedraRepository);
        cathedra = new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE);
        cathedras = singletonList(cathedra);
    }

    @Test
    void shouldCallCreateCathedra() {
        cathedraService.create(cathedra);

        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(cathedra);
    }

    @Test
    void shouldCallGetByCathedraId() {
        when(mockCathedraRepository.findById(ID_1_VALUE)).thenReturn(java.util.Optional.ofNullable(cathedra));
        Cathedra actualCathedra = cathedraService.getById(ID_1_VALUE);

        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);

        assertEquals(cathedra, actualCathedra);
    }

    @Test
    void shouldCallGetAllCathedras() {
        when(mockCathedraRepository.findAll()).thenReturn(cathedras);
        List<Cathedra> actualCathedras = cathedraService.getAll();

        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(cathedras, actualCathedras);
    }

    @Test
    void shouldCallGetAllCathedrasPageable() {
        cathedras = new ArrayList<>();
        cathedras.add(new Cathedra(ID_1_VALUE, CATHEDRA_1_NAME_VALUE));
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        Page<Cathedra> expectedPageCathedras = new PageImpl<>(cathedras, pageable, cathedras.size());

        when(mockCathedraRepository.findAll(pageable)).thenReturn(expectedPageCathedras);

        Page<Cathedra> actualPageStudents = cathedraService.getAll(pageable);

        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedPageCathedras, actualPageStudents);
    }


    @Test
    void shouldCallUpdateCathedra() {
        cathedraService.update(cathedra);

        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(cathedra);
    }

    @Test
    void shouldCallDeleteCathedra() {
        cathedraService.delete(ID_1_VALUE);

        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldThrowServiceExceptionWhenCantGetByCathedraId() {
        String message = format(ENTITY_NOT_FOUND, CATHEDRA);
        doThrow(new EntityNotFoundException(message)).when(mockCathedraRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> mockCathedraRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}