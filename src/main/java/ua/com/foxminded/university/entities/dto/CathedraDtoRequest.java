package ua.com.foxminded.university.entities.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CathedraDtoRequest {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";

    private static final String CATHEDRA_NAME_PROPERTY_VALUE = "The cathedra's name";


    @ApiModelProperty(value = CATHEDRA_NAME_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String name;
}