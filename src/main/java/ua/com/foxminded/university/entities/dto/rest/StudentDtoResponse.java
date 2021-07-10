package ua.com.foxminded.university.entities.dto.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentDtoResponse {
    private static final String STUDENT_ID_PROPERTY_VALUE = "The unique id of the student";
    private static final String STUDENT_FIRST_NAME_PROPERTY_VALUE = "The student's first name";
    private static final String STUDENT_LAST_NAME_PROPERTY_VALUE = "The student's last name";
    private static final String STUDENT_PHONE_PROPERTY_VALUE = "The student's phone";
    private static final String GROUP_ID_PROPERTY_VALUE = "The unique id of the group";
    private static final String GROUP_NAME_PROPERTY_VALUE = "The group's name";


    @ApiModelProperty(value = STUDENT_ID_PROPERTY_VALUE)
    private int id;

    @ApiModelProperty(value = STUDENT_FIRST_NAME_PROPERTY_VALUE)
    private String firstName;

    @ApiModelProperty(value = STUDENT_LAST_NAME_PROPERTY_VALUE)
    private String lastName;

    @ApiModelProperty(value = STUDENT_PHONE_PROPERTY_VALUE)
    private String phone;

    @ApiModelProperty(value = GROUP_ID_PROPERTY_VALUE)
    private int groupId;

    @ApiModelProperty(value = GROUP_NAME_PROPERTY_VALUE)
    private String groupName;
}