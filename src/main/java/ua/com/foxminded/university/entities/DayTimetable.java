package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "day_timetables")
@NoArgsConstructor
public class DayTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(name = "lecture_hall")
    private String lectureHall;

    @Column(name = "subject")
    private String subject;

    @ManyToOne(cascade = {CascadeType.PERSIST,
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.MERGE})
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(cascade = {CascadeType.PERSIST,
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.MERGE})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = {CascadeType.PERSIST,
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.MERGE})
    @JoinColumn(name = "month_timetable_id")
    private MonthTimetable monthTimetable;

    public DayTimetable(int id, LocalTime startTime, String lectureHall, String subject) {
        this.id = id;
        this.startTime = startTime;
        this.lectureHall = lectureHall;
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTimetable that = (DayTimetable) o;
        return id == that.id && Objects.equals(startTime, that.startTime) && Objects.equals(lectureHall, that.lectureHall) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, lectureHall, subject);
    }

    @Override
    public String toString() {
        return "DayTimetable{" +
            "startTime=" + startTime +
            ", lectureHall='" + lectureHall + '\'' +
            ", subject='" + subject + '\'' +
            '}';
    }
}
