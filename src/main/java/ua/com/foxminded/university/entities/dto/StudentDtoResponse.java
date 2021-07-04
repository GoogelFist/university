package ua.com.foxminded.university.entities.dto;

import lombok.Data;

@Data
public class StudentDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private int groupId;
    private String groupName;
}