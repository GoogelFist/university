package ua.com.foxminded.university.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.MonthTimetable;
import ua.com.foxminded.university.entities.dto.MonthTimetableDtoRequest;
import ua.com.foxminded.university.entities.dto.MonthTimetableDtoResponse;
import ua.com.foxminded.university.entities.mapper.MonthTimetableMapper;
import ua.com.foxminded.university.service.MonthTimetableService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Api(tags = "Month timetable controller")
@RestController
@Slf4j
@RequestMapping("/api/month-timetables")
public class MonthTimetableRestController {
    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {} with id {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";
    private static final String ID = "id";
    private static final String MONTH_TIMETABLES = "monthTimetables";
    private static final String MONTH_TIMETABLE = "monthTimetable";

    private static final String GET_ALL_OPERATION_VALUE = "Method used to fetch all month timetables";
    private static final String GET_BY_ID_OPERATION_VALUE = "Method used to fetch a month timetable by ID";
    private static final String CREATE_OPERATION_VALUE = "Method used to save a new month timetable";
    private static final String UPDATE_OPERATION_VALUE = "Method used to update a month timetable by ID";
    private static final String DELETE_OPERATION_VALUE = "Method used to delete a month timetable by ID";


    private final MonthTimetableService monthTimetableService;

    @Autowired
    public MonthTimetableRestController(MonthTimetableService monthTimetableService) {
        this.monthTimetableService = monthTimetableService;
    }

    @ApiOperation(value = GET_ALL_OPERATION_VALUE)
    @GetMapping()
    public List<MonthTimetableDtoResponse> showMonthTimetables() {
        log.debug(format(SHOW_LOG_MESSAGE, MONTH_TIMETABLES));
        List<MonthTimetable> monthTimetables = monthTimetableService.getAll();
        return monthTimetables.stream().map(MonthTimetableMapper.INSTANCE::toMonthTimetableDtoResponse).collect(Collectors.toList());
    }

    @ApiOperation(value = GET_BY_ID_OPERATION_VALUE)
    @GetMapping("/{id}")
    public MonthTimetableDtoResponse showMonthTimetable(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, MONTH_TIMETABLE), id);
        MonthTimetable monthTimetable = monthTimetableService.getById(id);
        return MonthTimetableMapper.INSTANCE.toMonthTimetableDtoResponse(monthTimetable);
    }

    @ApiOperation(value = CREATE_OPERATION_VALUE)
    @PostMapping()
    public MonthTimetableDtoResponse saveMonthTimetable(@Valid @RequestBody MonthTimetableDtoRequest monthTimetableDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, MONTH_TIMETABLE), monthTimetableDtoRequest);
        MonthTimetable monthTimetable = MonthTimetableMapper.INSTANCE.toMonthTimetableEntity(monthTimetableDtoRequest);
        monthTimetableService.create(monthTimetable);
        return MonthTimetableMapper.INSTANCE.toMonthTimetableDtoResponse(monthTimetable);
    }

    @ApiOperation(value = UPDATE_OPERATION_VALUE)
    @PatchMapping("/{id}")
    public MonthTimetableDtoResponse update(@Valid @RequestBody MonthTimetableDtoRequest monthTimetableDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, MONTH_TIMETABLE), monthTimetableDtoRequest, id);
        MonthTimetable monthTimetable = MonthTimetableMapper.INSTANCE.toMonthTimetableEntity(monthTimetableDtoRequest);
        monthTimetableService.update(monthTimetable);
        return MonthTimetableMapper.INSTANCE.toMonthTimetableDtoResponse(monthTimetable);
    }

    @ApiOperation(value = DELETE_OPERATION_VALUE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, MONTH_TIMETABLE), id);
        monthTimetableService.delete(id);
    }
}