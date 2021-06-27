package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.Teacher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringJUnitConfig(DaoTestConfig.class)
@Transactional
class TeacherDAOImplTest {
    @Autowired
    private TeacherDAO teacherDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateTeacher() {
        Teacher expectedTeacher = new Teacher(ID_3_VALUE, TEACHER_3_FIRST_NAME_VALUE, TEACHER_3_LAST_NAME_VALUE, TEACHER_3_PHONE_VALUE, QUALIFICATION_1_VALUE);
        teacherDAO.create(expectedTeacher);
        Teacher actualTeacher = teacherDAO.getById(ID_3_VALUE);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void shouldGetTeacherByID() {
        Teacher expectedTeacher = new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE);
        Teacher actualTeacher = teacherDAO.getById(ID_1_VALUE);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void shouldGetAllTeachers() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE));
        expectedTeachers.add(new Teacher(ID_2_VALUE, TEACHER_2_FIRST_NAME_VALUE, TEACHER_2_LAST_NAME_VALUE, TEACHER_2_PHONE_VALUE, QUALIFICATION_2_VALUE));
        List<Teacher> actualTeachers = teacherDAO.getAll();

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldUpdateTeacher() {
        Teacher expectedTeacher = new Teacher(ID_1_VALUE, TEACHER_2_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE);
        teacherDAO.update(expectedTeacher);
        Teacher actualTeacher = teacherDAO.getById(ID_1_VALUE);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void delete() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE));
        teacherDAO.delete(ID_2_VALUE);
        List<Teacher> actualTeachers = teacherDAO.getAll();

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void shouldGetTeachersByCathedraId() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new Teacher(ID_1_VALUE, TEACHER_1_FIRST_NAME_VALUE, TEACHER_1_LAST_NAME_VALUE, TEACHER_1_PHONE_VALUE, QUALIFICATION_1_VALUE));

        List<Teacher> actualTeachers = teacherDAO.getByCathedraId(ID_1_VALUE);

        assertEquals(expectedTeachers, actualTeachers);
    }
}