package ua.com.foxminded.university.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "day_timetables")
public class DayTimetable {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String MUST_BE_POSITIVE = "Must be positive";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = FIELD_CAN_NOT_BE_EMPTY)
    @Column(name = "start_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Size(message = MUST_BE_POSITIVE)
    @Column(name = "lecture_hall")
    private String lectureHall;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Column(name = "subject")
    private String subject;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "month_timetable_id")
    private MonthTimetable monthTimetable;

    public DayTimetable(int id, LocalTime startTime, String lectureHall, String subject) {
        this.id = id;
        this.startTime = startTime;
        this.lectureHall = lectureHall;
        this.subject = subject;
    }
}
