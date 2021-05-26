package ua.com.foxminded.university.dao.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DaoTestConfig;
import ua.com.foxminded.university.entities.Cathedra;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class CathedraMapperTest {

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    @Autowired
    private CathedraMapper cathedraMapper;

    private final Cathedra expectedCathedra = new Cathedra(ID_1_VALUE, PHYSICS);

    @Test
    void shouldReturnCathedraWithCorrectSettings() throws SQLException {
        when(resultSet.getInt(ID)).thenReturn(ID_1_VALUE);
        when(resultSet.getString(NAME)).thenReturn(PHYSICS);

        Cathedra actualCathedra = cathedraMapper.mapRow(resultSet, 1);

        assertEquals(expectedCathedra, actualCathedra);
    }
}