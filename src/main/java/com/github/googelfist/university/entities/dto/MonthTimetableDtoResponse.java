package com.github.googelfist.university.entities.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MonthTimetableDtoResponse {
    private static final String MONTH_TIMETABLE_ID_PROPERTY_VALUE = "The unique id of the month timetable";
    private static final String DATE_OF_MONTH_TIMETABLE_PROPERTY_VALUE = "The date of month timetable";


    @ApiModelProperty(value = MONTH_TIMETABLE_ID_PROPERTY_VALUE)
    private int id;

    @ApiModelProperty(value = DATE_OF_MONTH_TIMETABLE_PROPERTY_VALUE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}