package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.DayTimetable;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class DayTimetableDAOImlTest {
    @Autowired
    private DayTimetableDAO dayTimetableDAO;

    @Autowired
    private DataSource dataSource;

    private DayTimetable expectedDayTimetable;
    private DayTimetable actualDayTimetable;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateDayTimetable() {
        expectedDayTimetable = new DayTimetable(ID_3_VALUE, DAY_TIMETABLE_3_TIME_VALUE, LECTURE_HALL_2_VALUE, SUBJECT_2_VALUE);
        dayTimetableDAO.create(expectedDayTimetable);
        actualDayTimetable = dayTimetableDAO.getById(ID_3_VALUE);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldGetDayTimetableByID() {
        expectedDayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE);

        actualDayTimetable = dayTimetableDAO.getById(ID_1_VALUE);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldGetAllDayTimetables() {
        List<DayTimetable> expectedTimetables = new ArrayList<>();

        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE));
        expectedTimetables.add(new DayTimetable(ID_2_VALUE, DAY_TIMETABLE_2_TIME_VALUE, LECTURE_HALL_3_VALUE, SUBJECT_3_VALUE));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getAll();

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldUpdateDayTimeTable() {

        expectedDayTimetable = new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_4_TIME_VALUE, LECTURE_HALL_4_VALUE, SUBJECT_4_VALUE);
        dayTimetableDAO.update(expectedDayTimetable);
        actualDayTimetable = dayTimetableDAO.getById(ID_1_VALUE);

        assertEquals(expectedDayTimetable, actualDayTimetable);
    }

    @Test
    void shouldDeleteDAyTimetable() {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE));

        dayTimetableDAO.delete(ID_2_VALUE);
        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getAll();

        assertEquals(expectedTimetables, actualDayTimetables);
    }

    @Test
    void shouldGetDayTimetableByMonthTimetableId() {
        List<DayTimetable> expectedTimetables = new ArrayList<>();
        expectedTimetables.add(new DayTimetable(ID_1_VALUE, DAY_TIMETABLE_1_TIME_VALUE, LECTURE_HALL_1_VALUE, SUBJECT_1_VALUE));

        List<DayTimetable> actualDayTimetables = dayTimetableDAO.getByMonthTimetableId(ID_1_VALUE);

        assertEquals(expectedTimetables, actualDayTimetables);
    }
}