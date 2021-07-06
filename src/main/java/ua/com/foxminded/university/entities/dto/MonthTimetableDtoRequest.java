package ua.com.foxminded.university.entities.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class MonthTimetableDtoRequest {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";

    private static final String MONTH_TIMETABLE_ID_PROPERTY_VALUE = "The unique id of the month timetable";
    private static final String DATE_TIMETABLE_ID_PROPERTY_VALUE = "The date of month timetable";


    @ApiModelProperty(value = DATE_TIMETABLE_ID_PROPERTY_VALUE)
    @NotNull(message = FIELD_CAN_NOT_BE_EMPTY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}