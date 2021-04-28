package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.MonthTimetableDAO;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.MonthTimetable;

import java.util.List;

@Service
public class MonthTimetableServiceImpl implements MonthTimetableService {
    private final MonthTimetableDAO monthTimetableDAO;
    private final DayTimeTableService dayTimeTableService;

    @Autowired
    public MonthTimetableServiceImpl(MonthTimetableDAO monthTimetableDAO, DayTimeTableService dayTimeTableService) {
        this.monthTimetableDAO = monthTimetableDAO;
        this.dayTimeTableService = dayTimeTableService;
    }

    @Override
    public void create(MonthTimetable monthTimetable) {
        monthTimetableDAO.create(monthTimetable);
    }

    @Override
    public MonthTimetable getById(int id) {
        MonthTimetable monthTimetableById = monthTimetableDAO.getById(id);
        setDayTimetablesInMonthTimetable(monthTimetableById);
        return monthTimetableById;
    }

    @Override
    public List<MonthTimetable> getAll() {
        List<MonthTimetable> monthTimetables = monthTimetableDAO.getAll();
        monthTimetables.forEach(this::setDayTimetablesInMonthTimetable);
        return monthTimetables;
    }

    @Override
    public void update(int id, MonthTimetable monthTimetable) {
        monthTimetableDAO.update(id, monthTimetable);
    }

    @Override
    public void delete(int id) {
        monthTimetableDAO.delete(id);
    }

    private void setDayTimetablesInMonthTimetable(MonthTimetable monthTimetable) {
        int id = monthTimetable.getId();
        List<DayTimetable> dayTimetablesByMonthTimetableId = dayTimeTableService.getByMonthTimetableId(id);
        monthTimetable.setDayTimetables(dayTimetablesByMonthTimetableId);
    }
}
