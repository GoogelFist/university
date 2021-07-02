package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.CathedraService;
import ua.com.foxminded.university.service.TeacherService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private static final String TEACHERS = "teachers";
    private static final String TEACHER = "teacher";
    private static final String ID = "id";
    private static final String TEACHERS_BY_CATHEDRA_ID = "teachersByCathedraId";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private static final String CATHEDRA_ID = "cathedraId";
    private static final String CATHEDRAS = "cathedras";

    private static final String GET_BY_CATHEDRA_ID_VIEW_NAME = "/teachers/teachers-by-cathedra-id";
    private static final String GET_BY_ID_VIEW_NAME = "/teachers/teacher-info";
    private static final String GET_ALL_VIEW_NAME = "/teachers/teachers";
    private static final String GET_NEW_TEACHER_VIEW_NAME = "/teachers/new-teacher";

    private static final String REDIRECT = "redirect:/%s";
    private static final String GET_EDIT_TEACHER_VIEW_NAME = "/teachers/teacher-update";


    private final TeacherService teacherService;
    private final CathedraService cathedraService;

    @Autowired
    public TeacherController(TeacherService teacherService, CathedraService cathedraService) {
        this.teacherService = teacherService;
        this.cathedraService = cathedraService;
    }

    @GetMapping()
    public String showAllTeachers(Model model, Pageable pageable) {
        Page<Teacher> teacherPage = teacherService.getAll(pageable);
        model.addAttribute(TEACHERS, teacherPage);

        int totalPages = teacherPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_ALL_VIEW_NAME;
    }

    @GetMapping("/by-cathedra/{cathedraId}")
    public String showTeachersByCathedraId(@PathVariable(CATHEDRA_ID) int cathedraId, Model model, Pageable pageable) {
        Page<Teacher> teacherPage = teacherService.getByCathedraId(cathedraId, pageable);
        model.addAttribute(TEACHERS_BY_CATHEDRA_ID, teacherPage);

        int totalPages = teacherPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_BY_CATHEDRA_ID_VIEW_NAME;
    }

    @GetMapping("/{id}")
    public String showTeacherById(@PathVariable(ID) int id, Model model) {
        Teacher teacherById = teacherService.getById(id);

        model.addAttribute(TEACHER, teacherById);
        return GET_BY_ID_VIEW_NAME;
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute(TEACHER) Teacher teacher, Model model) {
        List<Cathedra> cathedras = cathedraService.getAll();
        model.addAttribute(CATHEDRAS, cathedras);
        return GET_NEW_TEACHER_VIEW_NAME;
    }

    @PostMapping()
    public String createTeacher(@ModelAttribute(TEACHER) @Valid Teacher teacher, BindingResult bindingResult, Model model) {
        if (teacher.getCathedra() == null || teacher.getCathedra().getId() < 1) {
            bindingResult.rejectValue("cathedra.id", "error.teacher", "Choose a cathedra");
        }
        if (bindingResult.hasErrors()) {
            List<Cathedra> cathedras = cathedraService.getAll();
            model.addAttribute(CATHEDRAS, cathedras);
            return GET_NEW_TEACHER_VIEW_NAME;
        }
        teacherService.create(teacher);
        return format(REDIRECT, TEACHERS);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(ID) int id, Model model) {
        List<Cathedra> cathedras = cathedraService.getAll();
        model.addAttribute(CATHEDRAS, cathedras);
        Teacher teacherById = teacherService.getById(id);
        model.addAttribute(TEACHER, teacherById);
        return GET_EDIT_TEACHER_VIEW_NAME;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute(TEACHER) @Valid Teacher teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GET_EDIT_TEACHER_VIEW_NAME;
        }
        teacherService.update(teacher);
        return format(REDIRECT, TEACHERS);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) int id) {
        teacherService.delete(id);
        return format(REDIRECT, TEACHERS);
    }
}