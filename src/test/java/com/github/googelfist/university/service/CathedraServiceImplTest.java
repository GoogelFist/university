package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.dto.CathedraDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import com.github.googelfist.university.entities.Cathedra;
import com.github.googelfist.university.repository.CathedraRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static com.github.googelfist.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CathedraServiceImplTest {
    @Mock
    private CathedraRepository mockCathedraRepository;

    private CathedraService cathedraService;
    private Cathedra cathedra;
    private CathedraDto cathedraDto;
    private List<Cathedra> cathedras;

    @BeforeEach
    void setUp() {
        cathedraService = new CathedraServiceImpl(mockCathedraRepository);

        cathedra = new Cathedra();
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        cathedraDto = new CathedraDto();
        cathedraDto.setName(CATHEDRA_1_NAME_VALUE);

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
    void shouldCallGetAllDtoCathedrasPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Cathedra> pageCathedras = new PageImpl<>(cathedras, pageable, cathedras.size());
        when(mockCathedraRepository.findAll(pageable)).thenReturn(pageCathedras);

        List<CathedraDto> cathedrasDto = new ArrayList<>();
        cathedrasDto.add(cathedraDto);
        Page<CathedraDto> expectedCathedraDtoPage = new PageImpl<>(cathedrasDto, pageable, cathedrasDto.size());

        Page<CathedraDto> actualCathedrasDtoPage = cathedraService.getAllDto(pageable);

        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedCathedraDtoPage, actualCathedrasDtoPage);
    }

    @Test
    void shouldCallGetDtoCathedraById() {
        when(mockCathedraRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(cathedra));
        CathedraDto actualCathedraDto = cathedraService.getDtoById(ID_1_VALUE);
        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);

        assertEquals(cathedraDto, actualCathedraDto);
    }

    @Test
    void shouldCallCreateDtoCathedra() {
        cathedraService.createDto(cathedraDto);
        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(cathedra);
    }

    @Test
    void shouldCallUpdateDtoCathedra() {
        cathedraService.updateDto(cathedraDto);
        verify(mockCathedraRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(cathedra);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCantGetByCathedraId() {
        String message = format(ENTITY_NOT_FOUND, CATHEDRA);
        doThrow(new EntityNotFoundException(message)).when(mockCathedraRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> mockCathedraRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}