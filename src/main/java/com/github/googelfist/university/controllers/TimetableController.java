package com.github.googelfist.university.controllers;

import com.github.googelfist.university.entities.dto.TimetableDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.github.googelfist.university.service.GroupService;
import com.github.googelfist.university.service.TeacherService;
import com.github.googelfist.university.service.TimetableService;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/timetables")
public class TimetableController {
    private static final String ID = "id";
    private static final String GROUPS = "groups";
    private static final String TEACHERS = "teachers";
    private static final String TIMETABLE_DTO = "timetableDto";
    private static final String TIMETABLES = "timetables";
    private static final String TIMETABLE = "timetable";
    private static final String PAGE_NUMBERS = "pageNumbers";

    private static final String GET_ALL_VIEW_NAME = "/timetables/timetables";
    private static final String GET_BY_ID_VIEW_NAME = "/timetables/timetable-info";
    private static final String GET_NEW_TIMETABLE_VIEW_NAME = "/timetables/timetable-new";
    private static final String GET_EDIT_TIMETABLE_VIEW_NAME = "/timetables/timetable-update";

    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String GET_SAVE_LOG_MESSAGE = "getting save new %s";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String GET_UPDATE_LOG_MESSAGE = "getting update %s with id {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";

    private static final String REDIRECT = "redirect:/%s";

    private final TimetableService timetableService;
    private final GroupService groupService;
    private final TeacherService teacherService;

    @GetMapping()
    public ModelAndView showAllTimetables(Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, TIMETABLES));
        Page<TimetableDto> timetablesPage = timetableService.getAllDto(pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_ALL_VIEW_NAME);
        modelAndView.addObject(TIMETABLES, timetablesPage);
        int totalPages = timetablesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showTimetableById(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, TIMETABLE), id);
        TimetableDto timetableDto = timetableService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_ID_VIEW_NAME);
        modelAndView.addObject(TIMETABLE_DTO, timetableDto);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newTimetable() {
        log.debug(format(GET_SAVE_LOG_MESSAGE, TIMETABLE));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_NEW_TIMETABLE_VIEW_NAME);
        modelAndView.addObject(TIMETABLE_DTO, new TimetableDto());
        modelAndView.addObject(GROUPS, groupService.getAll());
        modelAndView.addObject(TEACHERS, teacherService.getAll());
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView newTimetable(@Valid TimetableDto timetableDto, BindingResult bindingResult) {
        log.debug(format(SAVE_LOG_MESSAGE, TIMETABLE), timetableDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_NEW_TIMETABLE_VIEW_NAME);
            modelAndView.addObject(TIMETABLE_DTO, timetableDto);
            modelAndView.addObject(GROUPS, groupService.getAll());
            modelAndView.addObject(TEACHERS, teacherService.getAll());
        } else {
            timetableService.createDto(timetableDto);
            modelAndView.setViewName(format(REDIRECT, TIMETABLES));
        }
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editTimetable(@PathVariable(ID) int id) {
        log.debug(format(GET_UPDATE_LOG_MESSAGE, TIMETABLE), id);
        TimetableDto timetableDto = timetableService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_EDIT_TIMETABLE_VIEW_NAME);
        modelAndView.addObject(TIMETABLE_DTO, timetableDto);
        modelAndView.addObject(GROUPS, groupService.getAll());
        modelAndView.addObject(TEACHERS, teacherService.getAll());
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public ModelAndView editTimetable(@Valid TimetableDto timetableDto, BindingResult bindingResult) {
        log.debug(format(UPDATE_LOG_MESSAGE, TIMETABLE), timetableDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_EDIT_TIMETABLE_VIEW_NAME);
            modelAndView.addObject(TIMETABLE_DTO, timetableDto);
            modelAndView.addObject(GROUPS, groupService.getAll());
            modelAndView.addObject(TEACHERS, teacherService.getAll());
        } else {
            timetableService.updateDto(timetableDto);
            modelAndView.setViewName(format(REDIRECT, TIMETABLES));
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, TIMETABLE), id);
        timetableService.delete(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(format(REDIRECT, TIMETABLES));
        return modelAndView;
    }
}