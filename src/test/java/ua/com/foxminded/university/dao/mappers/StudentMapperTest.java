package ua.com.foxminded.university.dao.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.DaoTestConfig;
import ua.com.foxminded.university.entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(DaoTestConfig.class)
class StudentMapperTest {
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    @Autowired
    private StudentMapper studentMapper;

    private final Student expectedStudent = new Student(1, "James", "Gosling", "12345");

    @Test
    void shouldReturnStudentWithCorrectSettings() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("first_name")).thenReturn("James");
        when(resultSet.getString("last_name")).thenReturn("Gosling");
        when(resultSet.getString("phone")).thenReturn("12345");

        Student student = studentMapper.mapRow(resultSet, 1);

        assertEquals(expectedStudent, student);
    }
}