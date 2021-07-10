package ua.com.foxminded.university.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.dto.GroupDto;
import ua.com.foxminded.university.entities.dto.rest.GroupDtoRequest;
import ua.com.foxminded.university.entities.dto.rest.GroupDtoResponse;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(source = "cathedra.id", target = "cathedraId")
    @Mapping(source = "cathedra.name", target = "cathedraName")
    GroupDtoResponse toGroupDtoResponse(Group group);

    @Mapping(source = "cathedraId", target = "cathedra.id")
    Group toGroupEntity(GroupDtoRequest groupDtoRequest);

    @Mapping(source = "cathedra.id", target = "cathedraId")
    @Mapping(source = "cathedra.name", target = "cathedraName")
    GroupDto toGroupDto(Group group);

    @Mapping(source = "cathedraId", target = "cathedra.id")
    Group toGroupEntity(GroupDto groupDto);
}