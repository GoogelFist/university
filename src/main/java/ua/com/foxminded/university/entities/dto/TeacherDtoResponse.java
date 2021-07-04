package ua.com.foxminded.university.entities.dto;

import lombok.Data;

@Data
public class TeacherDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String qualification;
    private int cathedraId;
    private String cathedraName;
}