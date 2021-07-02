package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.service.MonthTimetableService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Controller
@RequestMapping("/month-timetables")
public class MonthTimetableController {
    private static final String REDIRECT = "redirect:/%s";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private static final String MONTH_TIMETABLES = "monthTimetables";
    private static final String MONTH_TIMETABLES_VIEW_NAME = "month-timetables";
    private static final String MONTH_TIMETABLE = "monthTimetable";
    private static final String ID = "id";

    private static final String GET_ALL_VIEW_NAME = "/monthtimetables/month-timetables";
    private static final String GET_BY_ID_VIEW_NAME = "/monthtimetables/month-timetable-info";
    private static final String GET_EDIT_MONTH_TIMETABLE_VIEW_NAME = "/monthtimetables/month-timetable-update";
    private static final String GET_NEW_MONTH_TIMETABLE_VIEW_NAME = "/monthtimetables/new-month-timetable";

    private final MonthTimetableService monthTimetableService;

    @Autowired
    public MonthTimetableController(MonthTimetableService monthTimetableService) {
        this.monthTimetableService = monthTimetableService;
    }

    @GetMapping()
    public String showAllMonthTimetables(Model model, Pageable pageable) {
        Page<MonthTimetable> monthTimetables = monthTimetableService.getAll(pageable);
        model.addAttribute(MONTH_TIMETABLES, monthTimetables);

        int totalPages = monthTimetables.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_ALL_VIEW_NAME;
    }

    @GetMapping({"{id}"})
    public String showDayTimetableById(@PathVariable(ID) int id, Model model) {
        MonthTimetable monthTimetable = monthTimetableService.getById(id);
        model.addAttribute(MONTH_TIMETABLE, monthTimetable);
        return GET_BY_ID_VIEW_NAME;
    }

    @GetMapping("/new")
    public String newMonthTimetable(@ModelAttribute(MONTH_TIMETABLE) MonthTimetable monthTimetable) {
        return GET_NEW_MONTH_TIMETABLE_VIEW_NAME;
    }

    @PostMapping()
    public String createMonthTimetable(@ModelAttribute(MONTH_TIMETABLE) @Valid MonthTimetable monthTimetable, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GET_NEW_MONTH_TIMETABLE_VIEW_NAME;
        }
        monthTimetableService.create(monthTimetable);
        return format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(ID) int id, Model model) {
        MonthTimetable monthTimetableById = monthTimetableService.getById(id);
        model.addAttribute(MONTH_TIMETABLE, monthTimetableById);
        return GET_EDIT_MONTH_TIMETABLE_VIEW_NAME;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute(MONTH_TIMETABLE) @Valid MonthTimetable monthTimetable, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GET_EDIT_MONTH_TIMETABLE_VIEW_NAME;
        }
        monthTimetableService.update(monthTimetable);
        return format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) int id) {
        monthTimetableService.delete(id);
        return format(REDIRECT, MONTH_TIMETABLES_VIEW_NAME);
    }
}