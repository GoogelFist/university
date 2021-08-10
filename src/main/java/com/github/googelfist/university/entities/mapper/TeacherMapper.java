package com.github.googelfist.university.entities.mapper;

import com.github.googelfist.university.entities.dto.rest.TeacherDtoRequest;
import com.github.googelfist.university.entities.dto.rest.TeacherDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.github.googelfist.university.entities.Teacher;
import com.github.googelfist.university.entities.dto.TeacherDto;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    @Mapping(source = "cathedra.id", target = "cathedraId")
    @Mapping(source = "cathedra.name", target = "cathedraName")
    TeacherDtoResponse toTeacherDtoResponse(Teacher teacher);

    @Mapping(source = "cathedraId", target = "cathedra.id")
    Teacher toTeacherEntity(TeacherDtoRequest teacherDtoRequest);

    @Mapping(source = "cathedra.id", target = "cathedraId")
    @Mapping(source = "cathedra.name", target = "cathedraName")
    TeacherDto toTeacherDto(Teacher teacher);

    @Mapping(source = "cathedraId", target = "cathedra.id")
    Teacher toTeacherEntity(TeacherDto teacherDto);
}