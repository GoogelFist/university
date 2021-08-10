package com.github.googelfist.university.entities.dto.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherDtoResponse {
    private static final String TEACHER_ID_PROPERTY_VALUE = "The unique id of the teacher";
    private static final String TEACHER_FIRST_NAME_PROPERTY_VALUE = "The teacher's first name";
    private static final String TEACHER_LAST_NAME_PROPERTY_VALUE = "The teacher's last name";
    private static final String TEACHER_PHONE_PROPERTY_VALUE = "The teacher's phone";
    private static final String QUALIFICATION_PROPERTY_VALUE = "The teacher's qualification";
    private static final String CATHEDRA_ID_PROPERTY_VALUE = "The unique id of the cathedra";
    private static final String CATHEDRA_NAME_PROPERTY_VALUE = "The cathedra's name";


    @ApiModelProperty(value = TEACHER_ID_PROPERTY_VALUE)
    private int id;

    @ApiModelProperty(value = TEACHER_FIRST_NAME_PROPERTY_VALUE)
    private String firstName;

    @ApiModelProperty(value = TEACHER_LAST_NAME_PROPERTY_VALUE)
    private String lastName;

    @ApiModelProperty(value = TEACHER_PHONE_PROPERTY_VALUE)
    private String phone;

    @ApiModelProperty(value = QUALIFICATION_PROPERTY_VALUE)
    private String qualification;

    @ApiModelProperty(value = CATHEDRA_ID_PROPERTY_VALUE)
    private int cathedraId;

    @ApiModelProperty(value = CATHEDRA_NAME_PROPERTY_VALUE)
    private String cathedraName;
}