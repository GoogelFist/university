package com.github.googelfist.university.entities.mapper;

import com.github.googelfist.university.entities.dto.rest.GroupDtoRequest;
import com.github.googelfist.university.entities.dto.rest.GroupDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.entities.dto.GroupDto;

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