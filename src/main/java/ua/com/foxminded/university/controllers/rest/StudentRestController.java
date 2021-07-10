package ua.com.foxminded.university.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.dto.rest.StudentDtoRequest;
import ua.com.foxminded.university.entities.dto.rest.StudentDtoResponse;
import ua.com.foxminded.university.entities.mapper.StudentMapper;
import ua.com.foxminded.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Api(tags = "Student controller")
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

    private static final String GET_ALL_OPERATION_VALUE = "Method used to fetch all students";
    private static final String GET_BY_ID_OPERATION_VALUE = "Method used to fetch a student by ID";
    private static final String CREATE_OPERATION_VALUE = "Method used to save a new student";
    private static final String UPDATE_OPERATION_VALUE = "Method used to update a student by ID";
    private static final String DELETE_OPERATION_VALUE = "Method used to delete a student by ID";


    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = GET_ALL_OPERATION_VALUE)
    @GetMapping()
    public List<StudentDtoResponse> showStudents() {
        log.debug(format(SHOW_LOG_MESSAGE, STUDENTS));
        List<Student> students = studentService.getAll();
        return students.stream().map(StudentMapper.INSTANCE::toStudentDtoResponse).collect(Collectors.toList());
    }

    @ApiOperation(value = GET_BY_ID_OPERATION_VALUE)
    @GetMapping("/{id}")
    public StudentDtoResponse showStudent(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, STUDENT), id);
        Student student = studentService.getById(id);
        return StudentMapper.INSTANCE.toStudentDtoResponse(student);
    }

    @ApiOperation(value = CREATE_OPERATION_VALUE)
    @PostMapping()
    public StudentDtoResponse saveStudent(@Valid @RequestBody StudentDtoRequest studentDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, STUDENT), studentDtoRequest);
        Student student = StudentMapper.INSTANCE.toStudentEntity(studentDtoRequest);
        studentService.create(student);
        return StudentMapper.INSTANCE.toStudentDtoResponse(student);
    }

    @ApiOperation(value = UPDATE_OPERATION_VALUE)
    @PatchMapping("/{id}")
    public StudentDtoResponse update(@Valid @RequestBody StudentDtoRequest studentDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, STUDENT), studentDtoRequest, id);
        Student student = StudentMapper.INSTANCE.toStudentEntity(studentDtoRequest);
        studentService.update(student);
        return StudentMapper.INSTANCE.toStudentDtoResponse(student);
    }

    @ApiOperation(value = DELETE_OPERATION_VALUE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, STUDENT), id);
        studentService.delete(id);
    }
}
