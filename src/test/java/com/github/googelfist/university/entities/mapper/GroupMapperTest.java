package com.github.googelfist.university.entities.mapper;

import com.github.googelfist.university.entities.dto.rest.GroupDtoRequest;
import com.github.googelfist.university.entities.dto.rest.GroupDtoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.googelfist.university.entities.Cathedra;
import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.entities.dto.GroupDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.googelfist.university.utils.Constants.*;

class GroupMapperTest {
    private final Group group = new Group();
    private final GroupDto groupDto = new GroupDto();
    private final GroupDtoResponse groupDtoResponse = new GroupDtoResponse();
    private final GroupDtoRequest groupDtoRequest = new GroupDtoRequest();


    @BeforeEach
    void setUp() {
        Cathedra cathedra = new Cathedra();
        cathedra.setId(ID_1_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        group.setName(GROUP_1_NAME_VALUE);
        group.setCathedra(cathedra);

        groupDto.setName(GROUP_1_NAME_VALUE);
        groupDto.setCathedraId(ID_1_VALUE);
        groupDto.setCathedraName(CATHEDRA_1_NAME_VALUE);

        groupDtoResponse.setName(GROUP_1_NAME_VALUE);
        groupDtoResponse.setCathedraId(ID_1_VALUE);
        groupDtoResponse.setCathedraName(CATHEDRA_1_NAME_VALUE);

        groupDtoRequest.setName(GROUP_1_NAME_VALUE);
        groupDtoRequest.setCathedraId(ID_1_VALUE);

    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToGroupDtoResponse() {
        GroupDtoResponse actualGroupDtoResponse = GroupMapper.INSTANCE.toGroupDtoResponse(group);
        assertEquals(groupDtoResponse, actualGroupDtoResponse);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToGroupEntity() {
        Group actualGroup = GroupMapper.INSTANCE.toGroupEntity(groupDtoRequest);
        assertEquals(group, actualGroup);
    }

    @Test
    void shouldReturnCorrectDtoWhenConvertToGroupDto() {
        GroupDto actualGroupDto = GroupMapper.INSTANCE.toGroupDto(group);
        assertEquals(groupDto, actualGroupDto);
    }

    @Test
    void shouldReturnCorrectEntityWhenConvertToGroupEntityFromDto() {
        Group actualGroup = GroupMapper.INSTANCE.toGroupEntity(groupDto);
        assertEquals(group, actualGroup);
    }
}