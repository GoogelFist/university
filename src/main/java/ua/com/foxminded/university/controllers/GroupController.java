package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.service.CathedraService;
import ua.com.foxminded.university.service.GroupService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private static final String GROUP = "group";
    private static final String GROUPS = "groups";
    private static final String ID = "id";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private static final String CATHEDRA_ID = "cathedraId";
    private static final String GROUPS_BY_CATHEDRA_ID = "groupsByCathedraId";
    private static final String CATHEDRAS = "cathedras";

    private static final String GET_BY_CATHEDRA_ID_VIEW_NAME = "/groups/groups-by-cathedra-id";
    private static final String GET_BY_ID_VIEW_NAME = "/groups/group-info";
    private static final String GET_ALL_VIEW_NAME = "/groups/groups";
    private static final String GET_NEW_GROUP_VIEW_NAME = "/groups/new-group";

    private static final String REDIRECT = "redirect:/%s";
    private static final String GET_EDIT_GROUP_VIEW_NAME = "/groups/group-update";

    private final GroupService groupService;
    private final CathedraService cathedraService;

    @Autowired
    public GroupController(GroupService groupService, CathedraService cathedraService) {
        this.groupService = groupService;
        this.cathedraService = cathedraService;
    }

    @GetMapping()
    public String showAllGroups(Model model, Pageable pageable) {
        Page<Group> groupPage = groupService.getAll(pageable);
        model.addAttribute(GROUPS, groupPage);

        int totalPages = groupPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_ALL_VIEW_NAME;
    }

    @GetMapping("/by-cathedra/{cathedraId}")
    public String showGroupsByCathedraId(@PathVariable(CATHEDRA_ID) int cathedraId, Model model, Pageable pageable) {
        Page<Group> groupPage = groupService.getByCathedraId(cathedraId, pageable);
        model.addAttribute(GROUPS_BY_CATHEDRA_ID, groupPage);

        int totalPages = groupPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }
        return GET_BY_CATHEDRA_ID_VIEW_NAME;
    }

    @GetMapping("/{id}")
    public String showGroupById(@PathVariable(ID) int id, Model model) {
        Group groupById = groupService.getById(id);
        model.addAttribute(GROUP, groupById);
        return GET_BY_ID_VIEW_NAME;
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute(GROUP) Group group, Model model) {
        List<Cathedra> cathedras = cathedraService.getAll();
        model.addAttribute(CATHEDRAS, cathedras);
        return GET_NEW_GROUP_VIEW_NAME;
    }

    @PostMapping()
    public String createGroup(@ModelAttribute(GROUP) @Valid Group group, BindingResult bindingResult, Model model) {
        if (group.getCathedra() == null || group.getCathedra().getId() < 1) {
            bindingResult.rejectValue("cathedra.id", "error.group", "Choose a cathedra");
        }
        if (bindingResult.hasErrors()) {
            List<Cathedra> cathedras = cathedraService.getAll();
            model.addAttribute(CATHEDRAS, cathedras);
            return GET_NEW_GROUP_VIEW_NAME;
        }
        groupService.create(group);
        return format(REDIRECT, GROUPS);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(ID) int id, Model model) {
        List<Cathedra> cathedras = cathedraService.getAll();
        model.addAttribute(CATHEDRAS, cathedras);
        Group groupById = groupService.getById(id);
        model.addAttribute(GROUP, groupById);
        return GET_EDIT_GROUP_VIEW_NAME;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute(GROUP) @Valid Group group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GET_EDIT_GROUP_VIEW_NAME;
        }
        groupService.update(group);
        return format(REDIRECT, GROUPS);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) int id) {
        groupService.delete(id);
        return format(REDIRECT, GROUPS);
    }
}