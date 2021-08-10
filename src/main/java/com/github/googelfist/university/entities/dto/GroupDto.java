package com.github.googelfist.university.entities.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class GroupDto {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String CHOOSE_A_CATHEDRA = "Choose a cathedra";

    private int id;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String name;

    @Positive(message = CHOOSE_A_CATHEDRA)
    private int cathedraId;

    private String cathedraName;
}