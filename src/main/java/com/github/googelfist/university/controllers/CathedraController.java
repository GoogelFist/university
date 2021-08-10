package com.github.googelfist.university.controllers;

import com.github.googelfist.university.entities.dto.CathedraDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.github.googelfist.university.service.CathedraService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/cathedras")
public class CathedraController {
    private static final String CATHEDRAS = "cathedras";
    private static final String CATHEDRA = "cathedra";
    private static final String CATHEDRA_DTO = "cathedraDto";
    private static final String ID = "id";
    private static final String PAGE_NUMBERS = "pageNumbers";

    private static final String GET_ALL_VIEW_NAME = "/cathedras/cathedras";
    private static final String GET_BY_ID_VIEW_NAME = "/cathedras/cathedra-info";
    private static final String GET_EDIT_CATHEDRA_VIEW_NAME = "/cathedras/cathedra-update";
    private static final String GET_NEW_CATHEDRA_VIEW_NAME = "/cathedras/cathedra-new";
    private static final String REDIRECT = "redirect:/%s";

    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String GET_SAVE_LOG_MESSAGE = "getting save new %s";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String GET_UPDATE_LOG_MESSAGE = "getting update %s with id {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";


    private final CathedraService cathedraService;

    @GetMapping()
    public ModelAndView showAllCathedras(Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, CATHEDRAS));
        Page<CathedraDto> cathedraPage = cathedraService.getAllDto(pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_ALL_VIEW_NAME);
        modelAndView.addObject(CATHEDRAS, cathedraPage);
        int totalPages = cathedraPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showCathedraById(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, CATHEDRA), id);
        CathedraDto cathedraDto = cathedraService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_ID_VIEW_NAME);
        modelAndView.addObject(CATHEDRA_DTO, cathedraDto);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newCathedra() {
        log.debug(format(GET_SAVE_LOG_MESSAGE, CATHEDRA));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_NEW_CATHEDRA_VIEW_NAME);
        modelAndView.addObject(CATHEDRA_DTO, new CathedraDto());
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView newCathedra(@Valid CathedraDto cathedraDto, BindingResult bindingResult) {
        log.debug(format(SAVE_LOG_MESSAGE, CATHEDRA), cathedraDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_NEW_CATHEDRA_VIEW_NAME);
            modelAndView.addObject(CATHEDRA_DTO, cathedraDto);
        } else {
            cathedraService.createDto(cathedraDto);
            modelAndView.setViewName(format(REDIRECT, CATHEDRAS));
        }
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editCathedra(@PathVariable(ID) int id) {
        log.debug(format(GET_UPDATE_LOG_MESSAGE, CATHEDRA), id);
        CathedraDto cathedraDto = cathedraService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_EDIT_CATHEDRA_VIEW_NAME);
        modelAndView.addObject(CATHEDRA_DTO, cathedraDto);
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public ModelAndView editCathedra(@Valid CathedraDto cathedraDto, BindingResult bindingResult) {
        log.debug(format(UPDATE_LOG_MESSAGE, CATHEDRA), cathedraDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_EDIT_CATHEDRA_VIEW_NAME);
            modelAndView.addObject(CATHEDRA_DTO, cathedraDto);
        } else {
            cathedraService.updateDto(cathedraDto);
            modelAndView.setViewName(format(REDIRECT, CATHEDRAS));
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, CATHEDRA), id);
        cathedraService.delete(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(format(REDIRECT, CATHEDRAS));
        return modelAndView;
    }
}