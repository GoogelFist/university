package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Controller
@RequestMapping("/students")
public class StudentController {
    private static final String REDIRECT = "redirect:/%s";
    private static final String STUDENTS = "students";
    private static final String GROUPS = "groups";
    private static final String STUDENT = "student";
    private static final String STUDENTS_BY_GROUP_ID = "studentsByGroupId";
    private static final String ID = "id";
    private static final String GROUP_ID = "groupId";
    private static final String PAGE_NUMBERS = "pageNumbers";

    private static final String GET_ALL_VIEW_NAME = "/students/students";
    private static final String GET_BY_GROUP_ID_VIEW_NAME = "/students/students-by-group-id";
    private static final String GET_BY_ID_VIEW_NAME = "/students/student-info";
    private static final String GET_NEW_STUDENT_VIEW_NAME = "/students/new-student";
    private static final String GET_EDIT_STUDENT_VIEW_NAME = "/students/student-update";

    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping()
    public String showAllStudents(Model model, Pageable pageable) {
        Page<Student> studentPage = studentService.getAll(pageable);
        model.addAttribute(STUDENTS, studentPage);

        int totalPages = studentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_ALL_VIEW_NAME;
    }

    @GetMapping("/by-group/{groupId}")
    public String showStudentsByGroupId(@PathVariable(GROUP_ID) int groupId, Model model, Pageable pageable) {
        Page<Student> studentPage = studentService.getByGroupId(groupId, pageable);
        model.addAttribute(STUDENTS_BY_GROUP_ID, studentPage);

        int totalPages = studentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_BY_GROUP_ID_VIEW_NAME;
    }

    @GetMapping("/{id}")
    public String showStudentById(@PathVariable(ID) int id, Model model) {
        Student studentById = studentService.getById(id);
        model.addAttribute(STUDENT, studentById);
        return GET_BY_ID_VIEW_NAME;
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute(STUDENT) Student student, Model model) {
        List<Group> groups = groupService.getAll();
        model.addAttribute(GROUPS, groups);
        return GET_NEW_STUDENT_VIEW_NAME;
    }

    @PostMapping()
    public String createStudent(@ModelAttribute(STUDENT) @Valid Student student, BindingResult bindingResult, Model model) {
        if (student.getGroup() == null || student.getGroup().getId() < 1) {
            bindingResult.rejectValue("group.id", "error.student", "Choose a group");
        }
        if (bindingResult.hasErrors()) {
            List<Group> groups = groupService.getAll();
            model.addAttribute(GROUPS, groups);
            return GET_NEW_STUDENT_VIEW_NAME;
        }
        studentService.create(student);
        return format(REDIRECT, STUDENTS);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(ID) int id, Model model) {
        List<Group> groups = groupService.getAll();
        model.addAttribute(GROUPS, groups);
        Student studentById = studentService.getById(id);
        model.addAttribute(STUDENT, studentById);
        return GET_EDIT_STUDENT_VIEW_NAME;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute(STUDENT) @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GET_EDIT_STUDENT_VIEW_NAME;
        }
        studentService.update(student);
        return format(REDIRECT, STUDENTS);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) int id) {
        studentService.delete(id);
        return format(REDIRECT, STUDENTS);
    }
}