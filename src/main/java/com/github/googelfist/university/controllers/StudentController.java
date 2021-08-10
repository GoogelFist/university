package com.github.googelfist.university.controllers;

import com.github.googelfist.university.entities.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.github.googelfist.university.service.GroupService;
import com.github.googelfist.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/students")
public class StudentController {
    private static final String STUDENTS = "students";
    private static final String GROUPS = "groups";
    private static final String STUDENT = "student";
    private static final String STUDENT_DTO = "studentDto";
    private static final String STUDENTS_BY_GROUP_ID = "studentsByGroupId";
    private static final String ID = "id";
    private static final String PAGE_NUMBERS = "pageNumbers";

    private static final String GET_ALL_VIEW_NAME = "/students/students";
    private static final String GET_BY_GROUP_ID_VIEW_NAME = "/students/students-by-group-id";
    private static final String GET_BY_ID_VIEW_NAME = "/students/student-info";
    private static final String GET_NEW_STUDENT_VIEW_NAME = "/students/student-new";
    private static final String GET_EDIT_STUDENT_VIEW_NAME = "/students/student-update";

    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String GET_SAVE_LOG_MESSAGE = "getting save new %s";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String GET_UPDATE_LOG_MESSAGE = "getting update %s with id {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";

    private static final String REDIRECT = "redirect:/%s";


    private final StudentService studentService;
    private final GroupService groupService;

    @GetMapping()
    public ModelAndView showAllStudents(Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, STUDENTS));
        Page<StudentDto> studentsPage = studentService.getAllDto(pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_ALL_VIEW_NAME);
        modelAndView.addObject(STUDENTS, studentsPage);
        int totalPages = studentsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/by-group/{id}")
    public ModelAndView showStudentsByGroupId(@PathVariable(ID) int id, Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, STUDENTS_BY_GROUP_ID));
        Page<StudentDto> studentsPage = studentService.getDtoByGroupId(id, pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_GROUP_ID_VIEW_NAME);
        modelAndView.addObject(STUDENTS, studentsPage);
        int totalPages = studentsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showStudentById(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, STUDENT), id);
        StudentDto studentDto = studentService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_ID_VIEW_NAME);
        modelAndView.addObject(STUDENT_DTO, studentDto);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newStudent() {
        log.debug(format(GET_SAVE_LOG_MESSAGE, STUDENT));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_NEW_STUDENT_VIEW_NAME);
        modelAndView.addObject(STUDENT_DTO, new StudentDto());
        modelAndView.addObject(GROUPS, groupService.getAll());
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView newStudent(@Valid StudentDto studentDto, BindingResult bindingResult) {
        log.debug(format(SAVE_LOG_MESSAGE, STUDENT), studentDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_NEW_STUDENT_VIEW_NAME);
            modelAndView.addObject(STUDENT_DTO, studentDto);
            modelAndView.addObject(GROUPS, groupService.getAll());
        } else {
            studentService.createDto(studentDto);
            modelAndView.setViewName(format(REDIRECT, STUDENTS));
        }
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editStudent(@PathVariable(ID) int id) {
        log.debug(format(GET_UPDATE_LOG_MESSAGE, STUDENT), id);
        StudentDto studentDto = studentService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_EDIT_STUDENT_VIEW_NAME);
        modelAndView.addObject(STUDENT_DTO, studentDto);
        modelAndView.addObject(GROUPS, groupService.getAll());
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public ModelAndView editStudent(@Valid StudentDto studentDto, BindingResult bindingResult) {
        log.debug(format(UPDATE_LOG_MESSAGE, STUDENT), studentDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_EDIT_STUDENT_VIEW_NAME);
            modelAndView.addObject(STUDENT_DTO, studentDto);
            modelAndView.addObject(GROUPS, groupService.getAll());
        } else {
            studentService.updateDto(studentDto);
            modelAndView.setViewName(format(REDIRECT, STUDENTS));
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, STUDENT), id);
        studentService.delete(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(format(REDIRECT, STUDENTS));
        return modelAndView;
    }
}