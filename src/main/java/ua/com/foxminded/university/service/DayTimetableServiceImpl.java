package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.DayTimetableDAO;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

@Service
public class DayTimetableServiceImpl implements DayTimeTableService {
    private final DayTimetableDAO dayTimetableDAO;
    private final GroupService groupService;
    private final TeacherService teacherService;

    @Autowired
    public DayTimetableServiceImpl(DayTimetableDAO dayTimetableDAO, GroupService groupService, TeacherService teacherService) {
        this.dayTimetableDAO = dayTimetableDAO;
        this.groupService = groupService;
        this.teacherService = teacherService;
    }

    @Override
    public void create(DayTimetable dayTimetable) {
        dayTimetableDAO.create(dayTimetable);
    }

    @Override
    public DayTimetable getById(int id) {
        DayTimetable dayTimetableById = dayTimetableDAO.getById(id);
        setGroupInDayTimetable(dayTimetableById);
        setTeacherInDayTimetable(dayTimetableById);
        return dayTimetableById;
    }

    @Override
    public List<DayTimetable> getAll() {
        List<DayTimetable> dayTimetables = dayTimetableDAO.getAll();
        setGroupsAndTeachersInDayTimetables(dayTimetables);
        return dayTimetables;
    }

    @Override
    public void update(int id, DayTimetable dayTimetable) {
        dayTimetableDAO.update(id, dayTimetable);
    }

    @Override
    public void delete(int id) {
        dayTimetableDAO.delete(id);
    }

    @Override
    public List<DayTimetable> getByGroupId(int groupId) {
        List<DayTimetable> dayTimetablesByGroupId = dayTimetableDAO.getByGroupId(groupId);
        dayTimetablesByGroupId.forEach(this::setTeacherInDayTimetable);
        return dayTimetablesByGroupId;
    }

    @Override
    public List<DayTimetable> getByTeacherId(int teacherId) {
        List<DayTimetable> dayTimetablesByTeacherId = dayTimetableDAO.getByTeacherId(teacherId);
        dayTimetablesByTeacherId.forEach(this::setGroupInDayTimetable);
        return dayTimetablesByTeacherId;
    }

    @Override
    public List<DayTimetable> getByMonthTimetableId(int id) {
        List<DayTimetable> dayTimetablesByMonthTimetableId = dayTimetableDAO.getByMonthTimetableId(id);
        setGroupsAndTeachersInDayTimetables(dayTimetablesByMonthTimetableId);
        return dayTimetablesByMonthTimetableId;
    }

    private void setGroupInDayTimetable(DayTimetable dayTimetable) {
        int id = dayTimetable.getGroup().getId();
        Group groupById = groupService.getById(id);
        dayTimetable.setGroup(groupById);
    }

    private void setTeacherInDayTimetable(DayTimetable dayTimetable) {
        int id = dayTimetable.getTeacher().getId();
        Teacher teacherById = teacherService.getById(id);
        dayTimetable.setTeacher(teacherById);
    }

    private void setGroupsAndTeachersInDayTimetables(List<DayTimetable> dayTimetables) {
        dayTimetables.forEach(dayTimetable -> {
            setGroupInDayTimetable(dayTimetable);
            setTeacherInDayTimetable(dayTimetable);
        });
    }
}