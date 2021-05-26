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
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.entities.Teacher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.foxminded.university.dao.exceptions.ExceptionsMessageConstants.ENTITY_NOT_FOUND;
import static ua.com.foxminded.university.utils.Constants.*;

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
    private MonthTimetable monthTimetable;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateDayTimetable() throws DaoException {
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_2_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);
        expectedDayTimetable = new DayTimetable(ID_3_VALUE, DAY_TIMETABLE_3_TIME_VALUE, LECTURE_HALL_2_VALUE, SUBJECT_2_VALUE, group, teacher, monthTimetable);
        dayTimetableDAO.create(expectedDayTimetable);
        actualDayTimetable = dayTimetableDAO.getById(ID_3_VALUE);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldGetDayTimetableByID() throws DaoException {
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_1_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);
        expectedDayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, monthTimetable);

        actualDayTimetable = dayTimetableDAO.getById(ID_1_VALUE);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionExceptionWhenCantGetDayTimetableById() {
        Exception exception = assertThrows(DaoException.class, () -> dayTimetableDAO.getById(ID_5_VALUE));
        String actual = exception.getMessage();
        String expected = format(ENTITY_NOT_FOUND, DAY_TIMETABLE, ID_5_VALUE);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllDayTimetables() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_1_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);
        Group group2 = new Group(ID_2_VALUE);
        Teacher teacher2 = new Teacher(ID_2_VALUE);
        MonthTimetable monthTimetable2 = new MonthTimetable(ID_2_VALUE);

        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, monthTimetable));
        expectedTimetables.add(new DayTimetable(ID_2_VALUE, DAY_TIMETABLE_2_TIME_VALUE, LECTURE_HALL_3_VALUE, SUBJECT_3_VALUE, group2, teacher2, monthTimetable2));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getAll();

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldUpdateDayTimeTable() throws DaoException {
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_1_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);

        expectedDayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_4_TIME_VALUE, LECTURE_HALL_4_VALUE, SUBJECT_4_VALUE, group, teacher, monthTimetable);
        dayTimetableDAO.update(ID_1_VALUE, expectedDayTimetable);
        actualDayTimetable = dayTimetableDAO.getById(ID_1_VALUE);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldDeleteDAyTimetable() throws DaoException {
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_1_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, monthTimetable));

        dayTimetableDAO.delete(ID_2_VALUE);
        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getAll();

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldGetDayTimetablesByGroupsId() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_1_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);
        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, monthTimetable));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getByGroupId(ID_1_VALUE);

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldGetDayTimetablesByTeacherId() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_1_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);
        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, monthTimetable));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getByTeacherId(ID_1_VALUE);

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldGetDayTimetableByMonthTimetableId() throws DaoException {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        group = new Group(ID_1_VALUE);
        teacher = new Teacher(ID_1_VALUE);
        monthTimetable = new MonthTimetable(ID_1_VALUE);
        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE, group, teacher, monthTimetable));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getByMonthTimetableId(ID_1_VALUE);

        assertEquals(expectedTimetables, actualDayTimetables);
    }
}