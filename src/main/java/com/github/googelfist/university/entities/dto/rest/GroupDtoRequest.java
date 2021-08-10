package com.github.googelfist.university.entities.dto.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class GroupDtoRequest {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String FIELD_VALUE_MUST_BE_POSITIVE = "This field value must be positive";

    private static final String CATHEDRA_ID_PROPERTY_VALUE = "The unique id of the cathedra";
    private static final String GROUP_NAME_PROPERTY_VALUE = "The group's name";


    @ApiModelProperty(value = GROUP_NAME_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String name;

    @ApiModelProperty(value = CATHEDRA_ID_PROPERTY_VALUE)
    @Positive(message = FIELD_VALUE_MUST_BE_POSITIVE)
    private int cathedraId;
}