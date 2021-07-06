package ua.com.foxminded.university.entities.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class TeacherDtoRequest {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String FIELD_VALUE_MUST_BE_POSITIVE = "This field value must be positive";
    private static final String PHONE_REGEXP = "^((\\+7|7|8)+([0-9]){10})$";
    private static final String ERROR_REGEXP_MESSAGE = "Incorrect phone number format. Example +79001231212";

    private static final String TEACHER_FIRST_NAME_PROPERTY_VALUE = "The teacher's first name";
    private static final String TEACHER_LAST_NAME_PROPERTY_VALUE = "The teacher's last name";
    private static final String TEACHER_PHONE_PROPERTY_VALUE = "The teacher's phone";
    private static final String QUALIFICATION_PROPERTY_VALUE = "The teacher's qualification";
    private static final String CATHEDRA_ID_PROPERTY_VALUE = "The unique id of the cathedra";


    @ApiModelProperty(value = TEACHER_FIRST_NAME_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String firstName;

    @ApiModelProperty(value = TEACHER_LAST_NAME_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String lastName;

    @ApiModelProperty(value = TEACHER_PHONE_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = PHONE_REGEXP, message = ERROR_REGEXP_MESSAGE)
    private String phone;

    @ApiModelProperty(value = QUALIFICATION_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String qualification;

    @ApiModelProperty(value = CATHEDRA_ID_PROPERTY_VALUE)
    @Positive(message = FIELD_VALUE_MUST_BE_POSITIVE)
    private int cathedraId;
}