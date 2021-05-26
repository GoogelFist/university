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
import ua.com.foxminded.university.entities.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class GroupMapperTest {
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    @Autowired
    private GroupMapper groupMapper;

    private final Group expectedGroup = new Group(ID_1_VALUE, GROUP_1_NAME_VALUE, new Cathedra(ID_1_VALUE));

    @Test
    void shouldReturnGroupWithCorrectSettings() throws SQLException {
        when(resultSet.getInt(COLUMN_LABEL_ID)).thenReturn(ID_1_VALUE);
        when(resultSet.getString(COLUMN_LABEL_NAME)).thenReturn(GROUP_1_NAME_VALUE);
        when(resultSet.getInt(COLUMN_LABEL_CATHEDRA_ID)).thenReturn(ID_1_VALUE);

        Group actualGroup = groupMapper.mapRow(resultSet, 1);

        assertEquals(expectedGroup, actualGroup);
    }
}