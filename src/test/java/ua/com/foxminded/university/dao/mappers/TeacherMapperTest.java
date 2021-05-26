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
import ua.com.foxminded.university.entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ua.com.foxminded.university.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class TeacherMapperTest {
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    void shouldReturnTeacherWithCorrectSettings() throws SQLException {
        Teacher expectedTeacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE, new Cathedra(ID_1_VALUE));

        when(resultSet.getInt(COLUMN_LABEL_ID)).thenReturn(ID_1_VALUE);
        when(resultSet.getString(COLUMN_LABEL_FIRST_NAME)).thenReturn(TEACHER_1_FIRST_NAME_VALUE);
        when(resultSet.getString(COLUMN_LABEL_LAST_NAME)).thenReturn(TEACHER_1_LAST_NAME_VALUE);
        when(resultSet.getString(COLUMN_LABEL_PHONE)).thenReturn(TEACHER_1_PHONE_VALUE);
        when(resultSet.getString(COLUMN_LABEL_QUALIFICATION)).thenReturn(QUALIFICATION_1_VALUE);
        when(resultSet.getInt(COLUMN_LABEL_CATHEDRA_ID)).thenReturn(ID_1_VALUE);

        Teacher actualTeacher = teacherMapper.mapRow(resultSet, 1);

        assertEquals(expectedTeacher, actualTeacher);
    }
}