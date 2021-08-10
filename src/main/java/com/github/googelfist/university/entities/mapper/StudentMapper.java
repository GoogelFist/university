package com.github.googelfist.university.entities.mapper;

import com.github.googelfist.university.entities.dto.rest.StudentDtoRequest;
import com.github.googelfist.university.entities.dto.rest.StudentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.github.googelfist.university.entities.Student;
import com.github.googelfist.university.entities.dto.StudentDto;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    StudentDtoResponse toStudentDtoResponse(Student student);

    @Mapping(source = "groupId", target = "group.id")
    Student toStudentEntity(StudentDtoRequest studentDtoRequest);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    StudentDto toStudentDto(Student student);

    @Mapping(source = "groupId", target = "group.id")
    Student toStudentEntity(StudentDto studentDto);
}