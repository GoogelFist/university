package ua.com.foxminded.university.entities.dto.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupDtoResponse {
    private static final String GROUP_ID_PROPERTY_VALUE = "The unique id of the group";
    private static final String GROUP_NAME_PROPERTY_VALUE = "The group's name";
    private static final String CATHEDRA_ID_PROPERTY_VALUE = "The unique id of the cathedra";
    private static final String CATHEDRA_NAME_PROPERTY_VALUE = "The cathedra's name";


    @ApiModelProperty(value = GROUP_ID_PROPERTY_VALUE)
    private int id;

    @ApiModelProperty(value = GROUP_NAME_PROPERTY_VALUE)
    private String name;

    @ApiModelProperty(value = CATHEDRA_ID_PROPERTY_VALUE)
    private int cathedraId;

    @ApiModelProperty(value = CATHEDRA_NAME_PROPERTY_VALUE)
    private String cathedraName;
}