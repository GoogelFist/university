package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.ENTITY_NOT_FOUND;

@SpringJUnitConfig(DaoTestConfig.class)
class DayTimetableDAOImlTest {

    private static final String DAY_TIMETABLE = "day_timetable";
    @Autowired
    private DayTimetableDAO dayTimetableDAO;

    @Autowired
    private DataSource dataSource;

    private DayTimetable expectedDayTimetable;
    private DayTimetable actualDayTimetable;
    private Group group;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/testData.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateDayTimetable() throws DaoException {
        group = new Group(1);
        teacher = new Teacher(2);
        expectedDayTimetable = new DayTimetable(3, LocalTime.of(12, 0), "223", "biology", group, teacher);
        dayTimetableDAO.create(expectedDayTimetable);
        actualDayTimetable = dayTimetableDAO.getById(3);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldGetDayTimetableByID() throws DaoException {
        group = new Group(1);
        teacher = new Teacher(1);
        expectedDayTimetable = new DayTimetable(1, LocalTime.of(8, 0), "112", "math", group, teacher);

        actualDayTimetable = dayTimetableDAO.getById(1);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionExceptionWhenCantGetDayTimetableById() {
        int id = 5;
        Exception exception = assertThrows(DaoException.class, () -> dayTimetableDAO.getById(id));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, DAY_TIMETABLE, id);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllDayTimetables() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(1);
        teacher = new Teacher(1);
        Group group2 = new Group(2);
        Teacher teacher2 = new Teacher(2);

        expectedTimetables.add(new DayTimetable(1, LocalTime.of(8, 0), "112", "math", group, teacher));
        expectedTimetables.add(new DayTimetable(2, LocalTime.of(10, 0), "312", "physic", group2, teacher2));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getAll();

        assertEquals(expectedTimetables, actualDayTimetables);
    }


    @Test
    void shouldUpdateDayTimeTable() throws DaoException {
        group = new Group(1);
        teacher = new Teacher(1);
        expectedDayTimetable = new DayTimetable(1, LocalTime.of(14, 0), "412", "arts", group, teacher);
        dayTimetableDAO.update(1, expectedDayTimetable);
        actualDayTimetable = dayTimetableDAO.getById(1);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldDeleteDAyTimetable() throws DaoException {
        group = new Group(1);
        teacher = new Teacher(1);
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        expectedTimetables.add(new DayTimetable(1, LocalTime.of(8, 0), "112", "math", group, teacher));

        dayTimetableDAO.delete(2);
        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getAll();

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldGetDayTimetablesByGroupsId() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(1);
        teacher = new Teacher(1);
        expectedTimetables.add(new DayTimetable(1, LocalTime.of(8, 0), "112", "math", group, teacher));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getByGroupId(1);

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldGetDayTimetablesByTeacherId() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(1);
        teacher = new Teacher(1);
        expectedTimetables.add(new DayTimetable(1, LocalTime.of(8, 0), "112", "math", group, teacher));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getByTeacherId(1);

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldGetDayTimetableByMonthTimetableId() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(1);
        teacher = new Teacher(1);
        Group group2 = new Group(2);
        Teacher teacher2 = new Teacher(2);
        expectedTimetables.add(new DayTimetable(1, LocalTime.of(8, 0), "112", "math", group, teacher));
        expectedTimetables.add(new DayTimetable(2, LocalTime.of(10, 0), "312", "physic", group2, teacher2));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getByMonthTimetableId(1);

        assertEquals(expectedTimetables, actualDayTimetables);
    }
}