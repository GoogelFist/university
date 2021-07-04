package ua.com.foxminded.university.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.dto.GroupDtoRequest;
import ua.com.foxminded.university.entities.dto.GroupDtoResponse;
import ua.com.foxminded.university.entities.mapper.GroupMapper;
import ua.com.foxminded.university.service.GroupService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
@Slf4j
@RequestMapping("/api/groups")
public class GroupRestController {
    private static final String SHOW_LOG_MESSAGE = "showing all %s";
    private static final String SHOW_BY_ID_LOG_MESSAGE = "showing %s with id {}";
    private static final String SAVE_LOG_MESSAGE = "saving new %s: {}";
    private static final String UPDATE_LOG_MESSAGE = "updating %s {} with id {}";
    private static final String DELETE_LOG_MESSAGE = "deleting %s with id {}";
    private static final String ID = "id";
    private static final String GROUPS = "groups";
    private static final String GROUP = "group";


    private final GroupService groupService;

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public List<GroupDtoResponse> showGroups() {
        log.debug(format(SHOW_LOG_MESSAGE, GROUPS));
        List<Group> groups = groupService.getAll();
        return groups.stream().map(GroupMapper.INSTANCE::toGroupDtoResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GroupDtoResponse showGroup(@PathVariable(ID) int id) {
        log.debug(format(SHOW_BY_ID_LOG_MESSAGE, GROUP), id);
        Group group = groupService.getById(id);
        return GroupMapper.INSTANCE.toGroupDtoResponse(group);
    }

    @PostMapping()
    public GroupDtoResponse saveGroup(@Valid @RequestBody GroupDtoRequest groupDtoRequest) {
        log.debug(format(SAVE_LOG_MESSAGE, GROUP), groupDtoRequest);
        Group group = GroupMapper.INSTANCE.toGroupEntity(groupDtoRequest);
        groupService.create(group);
        return GroupMapper.INSTANCE.toGroupDtoResponse(group);
    }

    @PatchMapping("/{id}")
    public GroupDtoResponse update(@Valid @RequestBody GroupDtoRequest groupDtoRequest, @PathVariable(ID) int id) {
        log.debug(format(UPDATE_LOG_MESSAGE, GROUP), groupDtoRequest, id);
        Group group = GroupMapper.INSTANCE.toGroupEntity(groupDtoRequest);
        groupService.update(group);
        return GroupMapper.INSTANCE.toGroupDtoResponse(group);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(ID) int id) {
        log.debug(format(DELETE_LOG_MESSAGE, GROUP), id);
        groupService.delete(id);
    }
}
