package ua.com.foxminded.university.entities.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimetableDtoResponse {
    private static final String TIMETABLE_ID_PROPERTY_VALUE = "The unique id of the timetable";
    private static final String DATE_OF_TIMETABLE_PROPERTY_VALUE = "The date of timetable";
    private static final String START_TIME_OF_TIMETABLE_PROPERTY_VALUE = "The start time of timetable";
    private static final String LECTURE_HALL_PROPERTY_VALUE = "The lecture hall of the timetable";
    private static final String SUBJECT_PROPERTY_VALUE = "The subject of the timetable";
    private static final String GROUP_ID_PROPERTY_VALUE = "The unique id of the group";
    private static final String GROUP_NAME_PROPERTY_VALUE = "The group's name";
    private static final String TEACHER_ID_PROPERTY_VALUE = "The unique id of the teacher";
    private static final String TEACHER_FIRST_NAME_PROPERTY_VALUE = "The teacher's first name";
    private static final String TEACHER_LAST_NAME_PROPERTY_VALUE = "The teacher's last name";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm";


    @ApiModelProperty(value = TIMETABLE_ID_PROPERTY_VALUE)
    private int id;

    @ApiModelProperty(value = DATE_OF_TIMETABLE_PROPERTY_VALUE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate date;

    @ApiModelProperty(value = START_TIME_OF_TIMETABLE_PROPERTY_VALUE)
    @DateTimeFormat(pattern = TIME_PATTERN)
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
}
