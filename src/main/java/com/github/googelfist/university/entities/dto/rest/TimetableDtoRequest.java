package com.github.googelfist.university.entities.dto.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimetableDtoRequest {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String FIELD_VALUE_MUST_BE_POSITIVE = "This field value must be positive";

    private static final String TIMETABLE_PROPERTY_VALUE = "The date of timetable";
    private static final String START_TIME_OF_TIMETABLE_PROPERTY_VALUE = "The start time of timetable";
    private static final String TEACHER_ID_PROPERTY_VALUE = "The unique id of the teacher";
    private static final String LECTURE_HALL_PROPERTY_VALUE = "The lecture hall of the timetable";
    private static final String SUBJECT_PROPERTY_VALUE = "The subject of the timetable";
    private static final String GROUP_ID_PROPERTY_VALUE = "The unique id of the group";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm";


    @ApiModelProperty(value = TIMETABLE_PROPERTY_VALUE)
    @NotNull(message = FIELD_CAN_NOT_BE_EMPTY)
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate date;

    @ApiModelProperty(value = START_TIME_OF_TIMETABLE_PROPERTY_VALUE)
    @NotNull(message = FIELD_CAN_NOT_BE_EMPTY)
    @DateTimeFormat(pattern = TIME_PATTERN)
    private LocalTime startTime;

    @ApiModelProperty(value = LECTURE_HALL_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String lectureHall;

    @ApiModelProperty(value = SUBJECT_PROPERTY_VALUE)
    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    private String subject;

    @ApiModelProperty(value = GROUP_ID_PROPERTY_VALUE)
    @Positive(message = FIELD_VALUE_MUST_BE_POSITIVE)
    private int groupId;

    @ApiModelProperty(value = TEACHER_ID_PROPERTY_VALUE)
    @Positive(message = FIELD_VALUE_MUST_BE_POSITIVE)
    private int teacherId;
}