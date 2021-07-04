package ua.com.foxminded.university.entities.dto;

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


    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String firstName;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String lastName;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = PHONE_REGEXP, message = ERROR_REGEXP_MESSAGE)
    private String phone;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String qualification;

    @Positive(message = FIELD_VALUE_MUST_BE_POSITIVE)
    private int cathedraId;
}