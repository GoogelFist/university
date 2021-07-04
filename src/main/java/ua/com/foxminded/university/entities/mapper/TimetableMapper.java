package ua.com.foxminded.university.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.com.foxminded.university.entities.Timetable;
import ua.com.foxminded.university.entities.dto.TimetableDto;
import ua.com.foxminded.university.entities.dto.TimetableDtoRequest;
import ua.com.foxminded.university.entities.dto.TimetableDtoResponse;

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