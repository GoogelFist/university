package ua.com.foxminded.university.dao.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DaoTestConfig;
import ua.com.foxminded.university.entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        Teacher expectedTeacher = new Teacher(1, "Jonathan", "Bride", "612345", "1");

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("first_name")).thenReturn("Jonathan");
        when(resultSet.getString("last_name")).thenReturn("Bride");
        when(resultSet.getString("phone")).thenReturn("612345");
        when(resultSet.getString("qualification")).thenReturn("1");

        Teacher actualTeacher = teacherMapper.mapRow(resultSet, 1);

        assertEquals(expectedTeacher, actualTeacher);
    }
}