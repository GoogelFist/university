package ua.com.foxminded.university.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Timetable;
import ua.com.foxminded.university.entities.dto.TimetableDtoRequest;
import ua.com.foxminded.university.entities.dto.TimetableDtoResponse;
import ua.com.foxminded.university.entities.mapper.TimetableMapper;
import ua.com.foxminded.university.service.TimetableService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Api(tags = "Timetable controller")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/timetables")
public class TimetableRestController {
    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {} with id {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";
    private static final String ID = "id";
    private static final String TIMETABLES = "timetables";
    private static final String TIMETABLE = "timetable";

    private static final String GET_ALL_OPERATION_VALUE = "Method used to fetch all timetables";
    private static final String GET_BY_ID_OPERATION_VALUE = "Method used to fetch a timetable by Id";
    private static final String CREATE_OPERATION_VALUE = "Method used to save a new timetable";
    private static final String UPDATE_OPERATION_VALUE = "Method used to update a timetable by Id";
    private static final String DELETE_OPERATION_VALUE = "Method used to delete a timetable by Id";

    private final TimetableService timetableService;

    @ApiOperation(value = GET_ALL_OPERATION_VALUE)
    @GetMapping()
    public List<TimetableDtoResponse> showTimetables() {
        log.debug(format(SHOW_LOG_MESSAGE, TIMETABLES));
        List<Timetable> timetables = timetableService.getAll();
        return timetables.stream().map(TimetableMapper.INSTANCE::toTimetableDtoResponse).collect(Collectors.toList());
    }

    @ApiOperation(value = GET_BY_ID_OPERATION_VALUE)
    @GetMapping("/{id}")
    public TimetableDtoResponse showTimetable(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, TIMETABLE), id);
        Timetable timetable = timetableService.getById(id);
        return TimetableMapper.INSTANCE.toTimetableDtoResponse(timetable);
    }

    @ApiOperation(value = CREATE_OPERATION_VALUE)
    @PostMapping()
    public TimetableDtoResponse saveTimetable(@Valid @RequestBody TimetableDtoRequest timetableDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, TIMETABLE), timetableDtoRequest);
        Timetable timetable = TimetableMapper.INSTANCE.toTimetableEntity(timetableDtoRequest);
        timetableService.create(timetable);
        return TimetableMapper.INSTANCE.toTimetableDtoResponse(timetable);
    }

    @ApiOperation(value = UPDATE_OPERATION_VALUE)
    @PatchMapping("/{id}")
    public TimetableDtoResponse update(@Valid @RequestBody TimetableDtoRequest timetableDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, TIMETABLE), timetableDtoRequest, id);
        Timetable timetable = TimetableMapper.INSTANCE.toTimetableEntity(timetableDtoRequest);
        timetableService.update(timetable);
        return TimetableMapper.INSTANCE.toTimetableDtoResponse(timetable);
    }

    @ApiOperation(value = DELETE_OPERATION_VALUE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, TIMETABLE), id);
        timetableService.delete(id);
    }
}