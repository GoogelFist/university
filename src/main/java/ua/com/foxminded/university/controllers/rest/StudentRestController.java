package ua.com.foxminded.university.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.dto.StudentDtoRequest;
import ua.com.foxminded.university.entities.dto.StudentDtoResponse;
import ua.com.foxminded.university.entities.mapper.StudentMapper;
import ua.com.foxminded.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
@Slf4j
@RequestMapping("/api/students")
public class StudentRestController {
    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {} with id {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";
    private static final String STUDENTS = "students";
    private static final String STUDENT = "student";
    private static final String ID = "id";


    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<StudentDtoResponse> showStudents() {
        log.debug(format(SHOW_LOG_MESSAGE, STUDENTS));
        List<Student> students = studentService.getAll();
        return students.stream().map(StudentMapper.INSTANCE::toStudentDtoResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StudentDtoResponse showStudent(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, STUDENT), id);
        Student student = studentService.getById(id);
        return StudentMapper.INSTANCE.toStudentDtoResponse(student);
    }

    @PostMapping()
    public StudentDtoResponse saveStudent(@Valid @RequestBody StudentDtoRequest studentDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, STUDENT), studentDtoRequest);
        Student student = StudentMapper.INSTANCE.toStudentEntity(studentDtoRequest);
        studentService.create(student);
        return StudentMapper.INSTANCE.toStudentDtoResponse(student);
    }

    @PatchMapping("/{id}")
    public StudentDtoResponse update(@Valid @RequestBody StudentDtoRequest studentDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, STUDENT), studentDtoRequest, id);
        Student student = StudentMapper.INSTANCE.toStudentEntity(studentDtoRequest);
        studentService.update(student);
        return StudentMapper.INSTANCE.toStudentDtoResponse(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, STUDENT), id);
        studentService.delete(id);
    }
}
