package ua.com.foxminded.university.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.dto.CathedraDtoRequest;
import ua.com.foxminded.university.entities.dto.CathedraDtoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.CATHEDRA_1_NAME_VALUE;
import static ua.com.foxminded.university.utils.Constants.ID_0_VALUE;

class CathedraMapperTest {
    private final Cathedra cathedra = new Cathedra();
    private final CathedraDtoResponse cathedraDtoResponse = new CathedraDtoResponse();
    private final CathedraDtoRequest cathedraDtoRequest = new CathedraDtoRequest();


    @BeforeEach
    void setUp() {
        cathedra.setId(ID_0_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        cathedraDtoResponse.setId(ID_0_VALUE);
        cathedraDtoResponse.setName(CATHEDRA_1_NAME_VALUE);

        cathedraDtoRequest.setName(CATHEDRA_1_NAME_VALUE);
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
}