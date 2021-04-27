package ua.com.foxminded.university.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MonthTimetable {
    private int id;
    private LocalDate date;
    private List<DayTimetable> dayTimetables;

    public MonthTimetable(int id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public MonthTimetable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<DayTimetable> getDayTimetables() {
        return dayTimetables;
    }

    public void setDayTimetables(List<DayTimetable> dayTimetables) {
        this.dayTimetables = dayTimetables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthTimetable that = (MonthTimetable) o;
        return id == that.id && Objects.equals(date, that.date) && Objects.equals(dayTimetables, that.dayTimetables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, dayTimetables);
    }

    @Override
    public String toString() {
        return "MonthTimetable{" +
            "id=" + id +
            ", date=" + date +
            ", dayTimetables=" + dayTimetables +
            '}';
    }
}