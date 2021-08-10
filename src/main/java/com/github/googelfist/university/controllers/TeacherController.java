package com.github.googelfist.university.controllers;

import com.github.googelfist.university.entities.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.github.googelfist.university.service.CathedraService;
import com.github.googelfist.university.service.TeacherService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private static final String TEACHERS = "teachers";
    private static final String TEACHER = "teacher";
    private static final String TEACHER_DTO = "teacherDto";
    private static final String ID = "id";
    private static final String TEACHERS_BY_CATHEDRA_ID = "teachersByCathedraId";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private static final String CATHEDRAS = "cathedras";

    private static final String GET_BY_CATHEDRA_ID_VIEW_NAME = "/teachers/teachers-by-cathedra-id";
    private static final String GET_BY_ID_VIEW_NAME = "/teachers/teacher-info";
    private static final String GET_ALL_VIEW_NAME = "/teachers/teachers";
    private static final String GET_NEW_TEACHER_VIEW_NAME = "/teachers/teacher-new";
    private static final String GET_EDIT_TEACHER_VIEW_NAME = "/teachers/teacher-update";

    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String GET_SAVE_LOG_MESSAGE = "getting save new %s";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String GET_UPDATE_LOG_MESSAGE = "getting update %s with id {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";

    private static final String REDIRECT = "redirect:/%s";


    private final TeacherService teacherService;
    private final CathedraService cathedraService;

    @GetMapping()
    public ModelAndView showAllTeachers(Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, TEACHERS));
        Page<TeacherDto> teacherPage = teacherService.getAllDto(pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_ALL_VIEW_NAME);
        modelAndView.addObject(TEACHERS, teacherPage);
        int totalPages = teacherPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/by-cathedra/{id}")
    public ModelAndView showTeachersByCathedraId(@PathVariable(ID) int id, Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, TEACHERS_BY_CATHEDRA_ID));
        Page<TeacherDto> teacherPage = teacherService.getDtoByCathedraId(id, pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_CATHEDRA_ID_VIEW_NAME);
        modelAndView.addObject(TEACHERS, teacherPage);
        int totalPages = teacherPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showTeacherById(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, TEACHER), id);
        TeacherDto teacherDto = teacherService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_ID_VIEW_NAME);
        modelAndView.addObject(TEACHER_DTO, teacherDto);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newTeacher() {
        log.debug(format(GET_SAVE_LOG_MESSAGE, TEACHER));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_NEW_TEACHER_VIEW_NAME);
        modelAndView.addObject(TEACHER_DTO, new TeacherDto());
        modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView newTeacher(@Valid TeacherDto teacherDto, BindingResult bindingResult) {
        log.debug(format(SAVE_LOG_MESSAGE, TEACHER), teacherDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_NEW_TEACHER_VIEW_NAME);
            modelAndView.addObject(TEACHER_DTO, teacherDto);
            modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        } else {
            teacherService.createDto(teacherDto);
            modelAndView.setViewName(format(REDIRECT, TEACHERS));
        }
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editTeacher(@PathVariable(ID) int id) {
        log.debug(format(GET_UPDATE_LOG_MESSAGE, TEACHER), id);
        TeacherDto teacherDto = teacherService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_EDIT_TEACHER_VIEW_NAME);
        modelAndView.addObject(TEACHER_DTO, teacherDto);
        modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public ModelAndView editTeacher(@Valid TeacherDto teacherDto, BindingResult bindingResult) {
        log.debug(format(UPDATE_LOG_MESSAGE, TEACHER), teacherDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_EDIT_TEACHER_VIEW_NAME);
            modelAndView.addObject(TEACHER_DTO, teacherDto);
            modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        } else {
            teacherService.updateDto(teacherDto);
            modelAndView.setViewName(format(REDIRECT, TEACHERS));
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, TEACHER), id);
        teacherService.delete(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(format(REDIRECT, TEACHERS));
        return modelAndView;
    }
}