package ua.com.foxminded.university.entities.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CathedraDto {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";

    private int id;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String name;
}