package ua.com.foxminded.university.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.dto.GroupDtoRequest;
import ua.com.foxminded.university.entities.dto.GroupDtoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.com.foxminded.university.utils.Constants.*;

class GroupMapperTest {
    private final Group group = new Group();
    private final GroupDtoResponse groupDtoResponse = new GroupDtoResponse();
    private final GroupDtoRequest groupDtoRequest = new GroupDtoRequest();


    @BeforeEach
    void setUp() {
        Cathedra cathedra = new Cathedra();
        cathedra.setId(ID_1_VALUE);
        cathedra.setName(CATHEDRA_1_NAME_VALUE);

        group.setId(ID_0_VALUE);
        group.setName(GROUP_1_NAME_VALUE);
        group.setCathedra(cathedra);

        groupDtoResponse.setId(ID_0_VALUE);
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
}