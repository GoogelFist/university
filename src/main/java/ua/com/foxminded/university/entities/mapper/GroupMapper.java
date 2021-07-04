package ua.com.foxminded.university.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.dto.GroupDtoRequest;
import ua.com.foxminded.university.entities.dto.GroupDtoResponse;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(source = "cathedra.id", target = "cathedraId")
    @Mapping(source = "cathedra.name", target = "cathedraName")
    GroupDtoResponse toGroupDtoResponse(Group group);

    @Mapping(source = "cathedraId", target = "cathedra.id")
    Group toGroupEntity(GroupDtoRequest groupDtoRequest);
}