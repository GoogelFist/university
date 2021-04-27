package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.com.foxminded.university.entities.MonthTimetable;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(DaoTestConfig.class)
class MonthTimetableDAOImplTest {

    @Autowired
    private MonthTimetableDAO monthTimetableDAO;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/testData.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void shouldCreateMonthTimetable() {
        MonthTimetable expectedMonthTimetable = new MonthTimetable(3, LocalDate.of(2021, 4, 25));
        monthTimetableDAO.create(expectedMonthTimetable);
        MonthTimetable actualMonthTimetable = monthTimetableDAO.getByID(3);

        assertEquals(expectedMonthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldGetMonthTimetableByID() {
        MonthTimetable expectedMonthTimetable = new MonthTimetable(1, LocalDate.of(2021, 4, 23));
        MonthTimetable actualMonthTimetable = monthTimetableDAO.getByID(1);

        assertEquals(expectedMonthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldGetAllMonthTimetables() {
        List<MonthTimetable> expectedMonthTimetables = new ArrayList<>();
        expectedMonthTimetables.add(new MonthTimetable(1, LocalDate.of(2021, 4, 23)));
        expectedMonthTimetables.add(new MonthTimetable(2, LocalDate.of(2021, 4, 24)));

        List<MonthTimetable> actualMonthTimetables = monthTimetableDAO.getAll();

        assertEquals(expectedMonthTimetables, actualMonthTimetables);
    }

    @Test
    void shouldUpdateMonthTimetable() {
        MonthTimetable expectedMonthTimetable = new MonthTimetable(1, LocalDate.of(2000, 1, 1));
        monthTimetableDAO.update(1, expectedMonthTimetable);
        MonthTimetable actualMonthTimetable = monthTimetableDAO.getByID(1);

        assertEquals(expectedMonthTimetable, actualMonthTimetable);
    }

    @Test
    void shouldDeleteMonthTimetable() {
        List<MonthTimetable> expectedMonthTimetables = new ArrayList<>();
        expectedMonthTimetables.add(new MonthTimetable(1, LocalDate.of(2021, 4, 23)));

        monthTimetableDAO.delete(2);
        List<MonthTimetable> actualMonthTimetables = monthTimetableDAO.getAll();

        assertEquals(expectedMonthTimetables, actualMonthTimetables);
    }
}