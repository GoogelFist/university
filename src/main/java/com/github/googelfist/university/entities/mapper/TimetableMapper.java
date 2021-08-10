package com.github.googelfist.university.entities.mapper;

import com.github.googelfist.university.entities.dto.rest.TimetableDtoRequest;
import com.github.googelfist.university.entities.dto.rest.TimetableDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.github.googelfist.university.entities.Timetable;
import com.github.googelfist.university.entities.dto.TimetableDto;

@Mapper
public interface TimetableMapper {
    TimetableMapper INSTANCE = Mappers.getMapper(TimetableMapper.class);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.firstName", target = "teacherFirstName")
    @Mapping(source = "teacher.lastName", target = "teacherLastName")
    TimetableDtoResponse toTimetableDtoResponse(Timetable timetable);

    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "teacherId", target = "teacher.id")
    Timetable toTimetableEntity(TimetableDtoRequest timetableDtoRequest);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.firstName", target = "teacherFirstName")
    @Mapping(source = "teacher.lastName", target = "teacherLastName")
    TimetableDto toTimetableDto(Timetable timetable);

    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "teacherId", target = "teacher.id")
    Timetable toTimetableEntity(TimetableDto timetableDto);
}