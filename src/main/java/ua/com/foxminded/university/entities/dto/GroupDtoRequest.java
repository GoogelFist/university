package ua.com.foxminded.university.entities.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class GroupDtoRequest {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String FIELD_VALUE_MUST_BE_POSITIVE = "This field value must be positive";

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String name;

    @Positive(message = FIELD_VALUE_MUST_BE_POSITIVE)
    private int cathedraId;
}