package ua.com.foxminded.university.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.dto.CathedraDtoRequest;
import ua.com.foxminded.university.entities.dto.CathedraDtoResponse;
import ua.com.foxminded.university.entities.mapper.CathedraMapper;
import ua.com.foxminded.university.service.CathedraService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
@Slf4j
@RequestMapping("/api/cathedras")
public class CathedraRestController {
    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {} with id {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";
    private static final String ID = "id";
    private static final String CATHEDRAS = "cathedras";
    private static final String CATHEDRA = "cathedra";


    private final CathedraService cathedraService;

    @Autowired
    public CathedraRestController(CathedraService cathedraService) {
        this.cathedraService = cathedraService;
    }

    @GetMapping()
    public List<CathedraDtoResponse> showCathedras() {
        log.debug(format(SHOW_LOG_MESSAGE, CATHEDRAS));
        List<Cathedra> cathedras = cathedraService.getAll();
        return cathedras.stream().map(CathedraMapper.INSTANCE::toCathedraDtoResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CathedraDtoResponse showCathedra(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, CATHEDRA), id);
        Cathedra cathedra = cathedraService.getById(id);
        return CathedraMapper.INSTANCE.toCathedraDtoResponse(cathedra);
    }

    @PostMapping()
    public CathedraDtoResponse saveCathedra(@Valid @RequestBody CathedraDtoRequest cathedraDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, CATHEDRA), cathedraDtoRequest);
        Cathedra cathedra = CathedraMapper.INSTANCE.toCathedraEntity(cathedraDtoRequest);
        cathedraService.create(cathedra);
        return CathedraMapper.INSTANCE.toCathedraDtoResponse(cathedra);
    }

    @PatchMapping("/{id}")
    public CathedraDtoResponse update(@Valid @RequestBody CathedraDtoRequest cathedraDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, CATHEDRA), cathedraDtoRequest, id);
        Cathedra cathedra = CathedraMapper.INSTANCE.toCathedraEntity(cathedraDtoRequest);
        cathedraService.update(cathedra);
        return CathedraMapper.INSTANCE.toCathedraDtoResponse(cathedra);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, CATHEDRA), id);
        cathedraService.delete(id);
    }
}