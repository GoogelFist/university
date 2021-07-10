package ua.com.foxminded.university.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.entities.dto.TeacherDto;
import ua.com.foxminded.university.entities.dto.rest.TeacherDtoRequest;
import ua.com.foxminded.university.entities.dto.rest.TeacherDtoResponse;

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