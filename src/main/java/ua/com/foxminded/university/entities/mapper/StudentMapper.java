package ua.com.foxminded.university.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.dto.StudentDtoRequest;
import ua.com.foxminded.university.entities.dto.StudentDtoResponse;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    StudentDtoResponse toStudentDtoResponse(Student student);

    @Mapping(source = "groupId", target = "group.id")
    Student toStudentEntity(StudentDtoRequest studentDtoRequest);
}