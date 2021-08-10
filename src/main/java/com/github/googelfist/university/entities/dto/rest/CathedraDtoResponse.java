package com.github.googelfist.university.entities.dto.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CathedraDtoResponse {
    private static final String CATHEDRA_ID_PROPERTY_VALUE = "The unique id of the cathedra";
    private static final String CATHEDRA_NAME_PROPERTY_VALUE = "The cathedra's name";


    @ApiModelProperty(value = CATHEDRA_ID_PROPERTY_VALUE)
    private int id;

    @ApiModelProperty(value = CATHEDRA_NAME_PROPERTY_VALUE)
    private String name;
}
