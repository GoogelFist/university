package ua.com.foxminded.university.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.DayTimetable;
import ua.com.foxminded.university.entities.dto.DayTimetableDtoRequest;
import ua.com.foxminded.university.entities.dto.DayTimetableDtoResponse;
import ua.com.foxminded.university.entities.mapper.DayTimetableMapper;
import ua.com.foxminded.university.service.DayTimeTableService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Api(tags = "Day timetable controller")
@RestController
@Slf4j
@RequestMapping("/api/day-timetables")
public class DayTimetableRestController {
    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {} with id {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";
    private static final String ID = "id";
    private static final String DAY_TIMETABLES = "dayTimetables";
    private static final String DAY_TIMETABLE = "dayTimetable";

    private static final String GET_ALL_OPERATION_VALUE = "Method used to fetch all day timetables";
    private static final String GET_BY_ID_OPERATION_VALUE = "Method used to fetch a day timetable by ID";
    private static final String CREATE_OPERATION_VALUE = "Method used to save a new day timetable";
    private static final String UPDATE_OPERATION_VALUE = "Method used to update a day timetable by ID";
    private static final String DELETE_OPERATION_VALUE = "Method used to delete a day timetable by ID";


    private final DayTimeTableService dayTimeTableService;

    @Autowired
    public DayTimetableRestController(DayTimeTableService dayTimeTableService) {
        this.dayTimeTableService = dayTimeTableService;
    }

    @ApiOperation(value = GET_ALL_OPERATION_VALUE)
    @GetMapping()
    public List<DayTimetableDtoResponse> showDayTimetables() {
        log.debug(format(SHOW_LOG_MESSAGE, DAY_TIMETABLES));
        List<DayTimetable> dayTimetables = dayTimeTableService.getAll();
        return dayTimetables.stream().map(DayTimetableMapper.INSTANCE::toDayTimetableDtoResponse).collect(Collectors.toList());
    }

    @ApiOperation(value = GET_BY_ID_OPERATION_VALUE)
    @GetMapping("/{id}")
    public DayTimetableDtoResponse showDayTimetable(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, DAY_TIMETABLE), id);
        DayTimetable dayTimetable = dayTimeTableService.getById(id);
        return DayTimetableMapper.INSTANCE.toDayTimetableDtoResponse(dayTimetable);
    }

    @ApiOperation(value = CREATE_OPERATION_VALUE)
    @PostMapping()
    public DayTimetableDtoResponse saveDayTimetable(@Valid @RequestBody DayTimetableDtoRequest dayTimetableDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, DAY_TIMETABLE), dayTimetableDtoRequest);
        DayTimetable monthTimetable = DayTimetableMapper.INSTANCE.toDayTimetableEntity(dayTimetableDtoRequest);
        dayTimeTableService.create(monthTimetable);
        return DayTimetableMapper.INSTANCE.toDayTimetableDtoResponse(monthTimetable);
    }

    @ApiOperation(value = UPDATE_OPERATION_VALUE)
    @PatchMapping("/{id}")
    public DayTimetableDtoResponse update(@Valid @RequestBody DayTimetableDtoRequest dayTimetableDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, DAY_TIMETABLE), dayTimetableDtoRequest, id);
        DayTimetable monthTimetable = DayTimetableMapper.INSTANCE.toDayTimetableEntity(dayTimetableDtoRequest);
        dayTimeTableService.update(monthTimetable);
        return DayTimetableMapper.INSTANCE.toDayTimetableDtoResponse(monthTimetable);
    }

    @ApiOperation(value = DELETE_OPERATION_VALUE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, DAY_TIMETABLE), id);
        dayTimeTableService.delete(id);
    }
}