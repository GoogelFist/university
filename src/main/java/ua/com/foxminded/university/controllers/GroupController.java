package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.foxminded.university.entities.dto.GroupDto;
import ua.com.foxminded.university.service.CathedraService;
import ua.com.foxminded.university.service.GroupService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/groups")
public class GroupController {
    private static final String GROUP = "group";
    private static final String GROUP_DTO = "groupDto";
    private static final String GROUPS = "groups";
    private static final String ID = "id";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private static final String GROUPS_BY_CATHEDRA_ID = "groupsByCathedraId";
    private static final String CATHEDRAS = "cathedras";

    private static final String GET_BY_CATHEDRA_ID_VIEW_NAME = "/groups/groups-by-cathedra-id";
    private static final String GET_BY_ID_VIEW_NAME = "/groups/group-info";
    private static final String GET_ALL_VIEW_NAME = "/groups/groups";
    private static final String GET_NEW_GROUP_VIEW_NAME = "/groups/group-new";
    private static final String GET_EDIT_GROUP_VIEW_NAME = "/groups/group-update";

    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String GET_SAVE_LOG_MESSAGE = "getting save new %s";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String GET_UPDATE_LOG_MESSAGE = "getting update %s with id {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";

    private static final String REDIRECT = "redirect:/%s";


    private final GroupService groupService;
    private final CathedraService cathedraService;

    @GetMapping()
    public ModelAndView showAllGroups(Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, GROUPS));
        Page<GroupDto> groupPage = groupService.getAllDto(pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_ALL_VIEW_NAME);
        modelAndView.addObject(GROUPS, groupPage);
        int totalPages = groupPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/by-cathedra/{id}")
    public ModelAndView showGroupsByCathedraId(@PathVariable(ID) int id, Pageable pageable) {
        log.debug(format(SHOW_LOG_MESSAGE, GROUPS_BY_CATHEDRA_ID));
        Page<GroupDto> groupPage = groupService.getDtoByCathedraId(id, pageable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_CATHEDRA_ID_VIEW_NAME);
        modelAndView.addObject(GROUPS, groupPage);
        int totalPages = groupPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject(PAGE_NUMBERS, pageNumbers);
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showGroupById(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, GROUP), id);
        GroupDto groupDto = groupService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_BY_ID_VIEW_NAME);
        modelAndView.addObject(GROUP_DTO, groupDto);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newGroup() {
        log.debug(format(GET_SAVE_LOG_MESSAGE, GROUP));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_NEW_GROUP_VIEW_NAME);
        modelAndView.addObject(GROUP_DTO, new GroupDto());
        modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView newGroup(@Valid GroupDto groupDto, BindingResult bindingResult) {
        log.debug(format(SAVE_LOG_MESSAGE, GROUP), groupDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_NEW_GROUP_VIEW_NAME);
            modelAndView.addObject(GROUP_DTO, groupDto);
            modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        } else {
            groupService.createDto(groupDto);
            modelAndView.setViewName(format(REDIRECT, GROUPS));
        }
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editGroup(@PathVariable(ID) int id) {
        log.debug(format(GET_UPDATE_LOG_MESSAGE, GROUP), id);
        GroupDto groupDto = groupService.getDtoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(GET_EDIT_GROUP_VIEW_NAME);
        modelAndView.addObject(GROUP_DTO, groupDto);
        modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public ModelAndView editGroup(@Valid GroupDto groupDto, BindingResult bindingResult) {
        log.debug(format(UPDATE_LOG_MESSAGE, GROUP), groupDto);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(GET_EDIT_GROUP_VIEW_NAME);
            modelAndView.addObject(GROUP_DTO, groupDto);
            modelAndView.addObject(CATHEDRAS, cathedraService.getAll());
        } else {
            groupService.updateDto(groupDto);
            modelAndView.setViewName(format(REDIRECT, GROUPS));
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, GROUP), id);
        groupService.delete(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(format(REDIRECT, GROUPS));
        return modelAndView;
    }
}