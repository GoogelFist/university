package ua.com.foxminded.university.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.dto.CathedraDto;
import ua.com.foxminded.university.entities.dto.rest.CathedraDtoRequest;
import ua.com.foxminded.university.entities.dto.rest.CathedraDtoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.CATHEDRA_1_NAME_VALUE;
import static ua.com.foxminded.university.utils.Constants.ID_0_VALUE;

class CathedraMapperTest {
    private final Cathedra cathedra = new Cathedra();
    private final CathedraDtoResponse cathedraDtoResponse = new CathedraDtoResponse();
    private final CathedraDtoRequest cathedraDtoRequest = new CathedraDtoRequest();
    private final CathedraDto cathedraDto = new CathedraDto();


    @BeforeEach
    void setUp() {
        cathedra.setId(ID_0_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        cathedraDtoResponse.setId(ID_0_VALUE);
        cathedraDtoResponse.setName(CATHEDRA_1_NAME_VALUE);

        cathedraDtoRequest.setName(CATHEDRA_1_NAME_VALUE);

        cathedraDto.setName(CATHEDRA_1_NAME_VALUE);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToCathedraDtoResponse() {
        CathedraDtoResponse actualCathedraDtoResponse = CathedraMapper.INSTANCE.toCathedraDtoResponse(cathedra);
        assertEquals(cathedraDtoResponse, actualCathedraDtoResponse);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToCathedraEntity() {
        Cathedra actualCathedra = CathedraMapper.INSTANCE.toCathedraEntity(cathedraDtoRequest);
        assertEquals(cathedra, actualCathedra);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToCathedraDto() {
        CathedraDto actualCathedraDto = CathedraMapper.INSTANCE.toCathedraDto(cathedra);
        assertEquals(cathedraDto, actualCathedraDto);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToCathedraEntityFromDto() {
        Cathedra actualCathedra = CathedraMapper.INSTANCE.toCathedraEntity(cathedraDto);
        assertEquals(cathedra, actualCathedra);
    }
}