package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.service.CathedraService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Controller
@RequestMapping("/cathedras")
public class CathedraController {
    private static final String CATHEDRAS = "cathedras";
    private static final String CATHEDRA = "cathedra";
    private static final String ID = "id";
    private static final String PAGE_NUMBERS = "pageNumbers";

    private static final String GET_ALL_VIEW_NAME = "/cathedras/cathedras";
    private static final String GET_BY_ID_VIEW_NAME = "/cathedras/cathedra-info";
    private static final String GET_EDIT_CATHEDRA_VIEW_NAME = "/cathedras/cathedra-update";
    private static final String GET_NEW_CATHEDRA_VIEW_NAME = "/cathedras/new-cathedra";
    private static final String REDIRECT = "redirect:/%s";

    private final CathedraService cathedraService;

    @Autowired
    public CathedraController(CathedraService cathedraService) {
        this.cathedraService = cathedraService;
    }

    @GetMapping()
    public String showAllCathedras(Model model, Pageable pageable) {
        Page<Cathedra> cathedraPage = cathedraService.getAll(pageable);

        model.addAttribute(CATHEDRAS, cathedraPage);

        int totalPages = cathedraPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_ALL_VIEW_NAME;
    }

    @GetMapping("/{id}")
    public String showCathedraById(@PathVariable(ID) int id, Model model) {
        Cathedra cathedraById = cathedraService.getById(id);

        model.addAttribute(CATHEDRA, cathedraById);
        return GET_BY_ID_VIEW_NAME;
    }

    @GetMapping("/new")
    public String newCathedra(@ModelAttribute(CATHEDRA) Cathedra cathedra) {
        return GET_NEW_CATHEDRA_VIEW_NAME;
    }

    @PostMapping()
    public String createCathedra(@ModelAttribute(CATHEDRA) Cathedra cathedra) {
        cathedraService.create(cathedra);
        return format(REDIRECT, CATHEDRAS);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(ID) int id, Model model) {
        Cathedra cathedraById = cathedraService.getById(id);
        model.addAttribute(CATHEDRA, cathedraById);
        return GET_EDIT_CATHEDRA_VIEW_NAME;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute(CATHEDRA) Cathedra cathedra) {
        cathedraService.update(cathedra);
        return format(REDIRECT, CATHEDRAS);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) int id) {
        cathedraService.delete(id);
        return format(REDIRECT, CATHEDRAS);
    }
}