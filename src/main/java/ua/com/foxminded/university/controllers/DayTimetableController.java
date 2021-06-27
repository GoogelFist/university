package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.DayTimeTableService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Controller
@RequestMapping("/day-timetables")
public class DayTimetableController {
    private static final String REDIRECT = "redirect:/%s";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private static final String MONTH_TIMETABLES_VIEW_NAME = "month-timetables";
    private static final String ID = "id";
    private static final String GROUPS = "groups";
    private static final String TEACHERS = "teachers";
    private static final String DAY_TIMETABLE_BY_MONTH_TIMETABLE = "dayTimetablesByMonthTimetable";
    private static final String DAY_TIMETABLE = "dayTimetable";
    private static final String MONTH_TIMETABLE_ID = "monthTimetableId";

    private static final String GET_BY_ID_VIEW_NAME = "/daytimetables/day-timetable-info";
    private static final String GET_BY_MONTH_TIMETABLES_ID_VIEW_NAME = "/daytimetables/day-timetables-by-month-timetable";
    private static final String GET_EDIT_DAY_TIMETABLE_VIEW_NAME = "/daytimetables/day-timetable-update";
    private static final String GET_NEW_DAY_TIMETABLE_VIEW_NAME = "/daytimetables/new-day-timetable";


    public final GroupService groupService;
    public final TeacherService teacherService;
    private final DayTimeTableService dayTimeTableService;

    @Autowired
    public DayTimetableController(DayTimeTableService dayTimeTableService, GroupService groupService, TeacherService teacherService) {
        this.dayTimeTableService = dayTimeTableService;
        this.groupService = groupService;
        this.teacherService = teacherService;
    }

    @GetMapping({"{id}"})
    public String showDayTimetableById(@PathVariable(ID) int id, Model model) {
        DayTimetable dayTimetable = dayTimeTableService.getById(id);
        model.addAttribute(DAY_TIMETABLE, dayTimetable);
        return GET_BY_ID_VIEW_NAME;
    }

    @GetMapping("/by-month-timetable/{id}")
    public String showDayTimetableByMonthTimetable(@PathVariable(ID) int id, Model model, Pageable pageable) {
        Page<DayTimetable> dayTimetablePage = dayTimeTableService.getByMonthTimetableId(id, pageable);
        model.addAttribute(DAY_TIMETABLE_BY_MONTH_TIMETABLE, dayTimetablePage);
        model.addAttribute(MONTH_TIMETABLE_ID, id);

        int totalPages = dayTimetablePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_BY_MONTH_TIMETABLES_ID_VIEW_NAME;
    }

    @GetMapping("/{id}/new")
    public String newDayTimetable(@PathVariable(ID) int id, @ModelAttribute(DAY_TIMETABLE) DayTimetable dayTimetable, Model model) {
        List<Teacher> allTeachers = teacherService.getAll();
        List<Group> allGroups = groupService.getAll();
        dayTimetable.setMonthTimetable(new MonthTimetable(id));
        model.addAttribute(GROUPS, allGroups);
        model.addAttribute(TEACHERS, allTeachers);
        return GET_NEW_DAY_TIMETABLE_VIEW_NAME;
    }

    @PostMapping()
    public String createDayTimetable(@ModelAttribute(DAY_TIMETABLE) DayTimetable dayTimetable) {
        dayTimeTableService.create(dayTimetable);
        return format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(ID) int id, Model model) {
        List<Teacher> allTeachers = teacherService.getAll();
        List<Group> allGroups = groupService.getAll();
        model.addAttribute(GROUPS, allGroups);
        model.addAttribute(TEACHERS, allTeachers);

        DayTimetable dayTimetableById = dayTimeTableService.getById(id);
        model.addAttribute(DAY_TIMETABLE, dayTimetableById);
        return GET_EDIT_DAY_TIMETABLE_VIEW_NAME;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute(DAY_TIMETABLE) DayTimetable dayTimetable) {
        dayTimeTableService.update(dayTimetable);
        return format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) int id) {
        dayTimeTableService.delete(id);
        return format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME);
    }
}