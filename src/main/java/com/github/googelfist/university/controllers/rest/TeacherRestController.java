package com.github.googelfist.university.controllers.rest;

import com.github.googelfist.university.entities.dto.rest.TeacherDtoRequest;
import com.github.googelfist.university.entities.dto.rest.TeacherDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.googelfist.university.entities.Teacher;
import com.github.googelfist.university.entities.mapper.TeacherMapper;
import com.github.googelfist.university.service.TeacherService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Api(tags = "Teacher controller")
@RestController
@Slf4j
@RequestMapping("/api/teachers")
public class TeacherRestController {
    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {} with id {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";
    private static final String TEACHERS = "teachers";
    private static final String TEACHER = "teacher";
    private static final String ID = "id";

    private static final String GET_ALL_OPERATION_VALUE = "Method used to fetch all teachers";
    private static final String GET_BY_ID_OPERATION_VALUE = "Method used to fetch a teacher by ID";
    private static final String CREATE_OPERATION_VALUE = "Method used to save a new teacher";
    private static final String UPDATE_OPERATION_VALUE = "Method used to update a teacher by ID";
    private static final String DELETE_OPERATION_VALUE = "Method used to delete a teacher by ID";


    private final TeacherService teacherService;

    @Autowired
    public TeacherRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation(value = GET_ALL_OPERATION_VALUE)
    @GetMapping()
    public List<TeacherDtoResponse> showTeachers() {
        log.debug(format(SHOW_LOG_MESSAGE, TEACHERS));
        List<Teacher> teachers = teacherService.getAll();
        return teachers.stream().map(TeacherMapper.INSTANCE::toTeacherDtoResponse).collect(Collectors.toList());
    }

    @ApiOperation(value = GET_BY_ID_OPERATION_VALUE)
    @GetMapping("/{id}")
    public TeacherDtoResponse showTeacher(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, TEACHER), id);
        Teacher teacher = teacherService.getById(id);
        return TeacherMapper.INSTANCE.toTeacherDtoResponse(teacher);
    }

    @ApiOperation(value = CREATE_OPERATION_VALUE)
    @PostMapping()
    public TeacherDtoResponse saveTeacher(@Valid @RequestBody TeacherDtoRequest teacherDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, TEACHER), teacherDtoRequest);
        Teacher teacher = TeacherMapper.INSTANCE.toTeacherEntity(teacherDtoRequest);
        teacherService.create(teacher);
        return TeacherMapper.INSTANCE.toTeacherDtoResponse(teacher);
    }

    @ApiOperation(value = UPDATE_OPERATION_VALUE)
    @PatchMapping("/{id}")
    public TeacherDtoResponse update(@Valid @RequestBody TeacherDtoRequest teacherDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, TEACHER), teacherDtoRequest, id);
        Teacher teacher = TeacherMapper.INSTANCE.toTeacherEntity(teacherDtoRequest);
        teacherService.update(teacher);
        return TeacherMapper.INSTANCE.toTeacherDtoResponse(teacher);
    }

    @ApiOperation(value = DELETE_OPERATION_VALUE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, TEACHER), id);
        teacherService.delete(id);
    }
}