package ua.com.foxminded.university.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "month_timetables")
public class MonthTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "monthTimetable")
    private List<DayTimetable> dayTimetables;

    public MonthTimetable(int id) {
        this.id = id;
    }

    public MonthTimetable(int id, LocalDate date) {
        this.id = id;
        this.date = date;
    }
}