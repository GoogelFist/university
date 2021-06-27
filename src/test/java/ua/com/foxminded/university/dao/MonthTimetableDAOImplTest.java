package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.entities.MonthTimetable;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

@SpringJUnitConfig(DaoTestConfig.class)
@Transactional
class MonthTimetableDAOImplTest {
    @Autowired
    private MonthTimetableDAO monthTimetableDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource(TEST_DATA_SQL_PATH));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateMonthTimetable() {
        MonthTimetable expectedMonthTimetable = new MonthTimetable(ID_3_VALUE, MONTH_TIMETABLE_DATE_VALUE_3);
        monthTimetableDAO.create(expectedMonthTimetable);
        MonthTimetable actualMonthTimetable = monthTimetableDAO.getById(ID_3_VALUE);

        assertEquals(expectedMonthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldGetMonthTimetableByID() {
        MonthTimetable expectedMonthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1);
        MonthTimetable actualMonthTimetable = monthTimetableDAO.getById(ID_1_VALUE);

        assertEquals(expectedMonthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldGetAllMonthTimetables() {
        List<MonthTimetable> expectedMonthTimetables = new ArrayList<>();
        expectedMonthTimetables.add(new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1));
        expectedMonthTimetables.add(new MonthTimetable(ID_2_VALUE, MONTH_TIMETABLE_DATE_VALUE_2));

        List<MonthTimetable> actualMonthTimetables = monthTimetableDAO.getAll();

        assertEquals(expectedMonthTimetables, actualMonthTimetables);
    }

    @Test
    void shouldUpdateMonthTimetable() {
        MonthTimetable expectedMonthTimetable = new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_3);
        monthTimetableDAO.update(expectedMonthTimetable);
        MonthTimetable actualMonthTimetable = monthTimetableDAO.getById(1);

        assertEquals(expectedMonthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldDeleteMonthTimetable() {
        List<MonthTimetable> expectedMonthTimetables = new ArrayList<>();
        expectedMonthTimetables.add(new MonthTimetable(ID_1_VALUE, MONTH_TIMETABLE_DATE_VALUE_1));

        monthTimetableDAO.delete(ID_2_VALUE);
        List<MonthTimetable> actualMonthTimetables = monthTimetableDAO.getAll();

        assertEquals(expectedMonthTimetables, actualMonthTimetables);
    }
}