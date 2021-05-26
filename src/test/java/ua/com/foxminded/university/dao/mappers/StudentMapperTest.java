package ua.com.foxminded.university.dao.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DaoTestConfig;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class StudentMapperTest {
    private final Student expectedStudent = new Student(ID_1_VALUE, STUDENT_1_FIRST_NAME_VALUE, STUDENT_1_LAST_NAME_VALUE, STUDENT_1_PHONE_VALUE, new Group(ID_1_VALUE));
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void shouldReturnStudentWithCorrectSettings() throws SQLException {
        when(resultSet.getInt(COLUMN_LABEL_ID)).thenReturn(ID_1_VALUE);
        when(resultSet.getString(COLUMN_LABEL_FIRST_NAME)).thenReturn(STUDENT_1_FIRST_NAME_VALUE);
        when(resultSet.getString(COLUMN_LABEL_LAST_NAME)).thenReturn(STUDENT_1_LAST_NAME_VALUE);
        when(resultSet.getString(COLUMN_LABEL_PHONE)).thenReturn(STUDENT_1_PHONE_VALUE);
        when(resultSet.getInt(COLUMN_LABEL_GROUP_ID)).thenReturn(ID_1_VALUE);

        Student student = studentMapper.mapRow(resultSet, 1);

        assertEquals(expectedStudent, student);
    }
}