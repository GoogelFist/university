package ua.com.foxminded.university.entities.dto;

import lombok.Data;

@Data
public class GroupDtoResponse {
    private int id;
    private String name;
    private int cathedraId;
    private String cathedraName;
}