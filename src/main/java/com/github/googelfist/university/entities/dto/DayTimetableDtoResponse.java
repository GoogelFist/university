package com.github.googelfist.university.entities.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DayTimetableDtoResponse {
    private static final String DAY_TIMETABLE_ID_PROPERTY_VALUE = "The unique id of the day timetable";
    private static final String START_TIME_OF_DAY_TIMETABLE_PROPERTY_VALUE = "The start time of day timetable";
    private static final String LECTURE_HALL_PROPERTY_VALUE = "The lecture hall of the day timetable";
    private static final String SUBJECT_PROPERTY_VALUE = "The subject of the day timetable";
    private static final String GROUP_ID_PROPERTY_VALUE = "The unique id of the group";
    private static final String GROUP_NAME_PROPERTY_VALUE = "The group's name";
    private static final String TEACHER_ID_PROPERTY_VALUE = "The unique id of the teacher";
    private static final String TEACHER_FIRST_NAME_PROPERTY_VALUE = "The teacher's first name";
    private static final String TEACHER_LAST_NAME_PROPERTY_VALUE = "The teacher's last name";
    private static final String MONTH_TIMETABLE_ID_PROPERTY_VALUE = "The unique id of the month timetable";
    private static final String DATE_OF_MONTH_TIMETABLE_PROPERTY_VALUE = "The date of month timetable";


    @ApiModelProperty(value = DAY_TIMETABLE_ID_PROPERTY_VALUE)
    private int id;

    @ApiModelProperty(value = START_TIME_OF_DAY_TIMETABLE_PROPERTY_VALUE)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty(value = LECTURE_HALL_PROPERTY_VALUE)
    private String lectureHall;

    @ApiModelProperty(value = SUBJECT_PROPERTY_VALUE)
    private String subject;

    @ApiModelProperty(value = GROUP_ID_PROPERTY_VALUE)
    private int groupId;

    @ApiModelProperty(value = GROUP_NAME_PROPERTY_VALUE)
    private String groupName;

    @ApiModelProperty(value = TEACHER_ID_PROPERTY_VALUE)
    private int teacherId;

    @ApiModelProperty(value = TEACHER_FIRST_NAME_PROPERTY_VALUE)
    private String teacherFirstName;

    @ApiModelProperty(value = TEACHER_LAST_NAME_PROPERTY_VALUE)
    private String teacherLastName;

    @ApiModelProperty(value = MONTH_TIMETABLE_ID_PROPERTY_VALUE)
    private int monthTimetableId;

    @ApiModelProperty(value = DATE_OF_MONTH_TIMETABLE_PROPERTY_VALUE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate monthTimetableDate;
}