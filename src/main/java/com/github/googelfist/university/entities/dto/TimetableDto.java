package com.github.googelfist.university.entities.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimetableDto {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String FIELD_VALUE_MUST_BE_POSITIVE = "This field value must be positive";
    private static final String CHOSE_A_GROUP = "Choose a group";
    private static final String CHOSE_A_TEACHER = "Choose a teacher";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm";


    private int id;

    @NotNull(message = FIELD_CAN_NOT_BE_EMPTY)
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate date;

    @NotNull(message = FIELD_CAN_NOT_BE_EMPTY)
    @DateTimeFormat(pattern = TIME_PATTERN)
    private LocalTime startTime;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String lectureHall;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String subject;

    @Positive(message = CHOSE_A_GROUP)
    private int groupId;

    private String groupName;

    @Positive(message = CHOSE_A_TEACHER)
    private int teacherId;

    private String teacherFirstName;

    private String teacherLastName;
}
