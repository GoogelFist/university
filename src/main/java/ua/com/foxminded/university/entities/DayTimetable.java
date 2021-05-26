package ua.com.foxminded.university.entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Objects;

public class DayTimetable {
    private int id;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    private String lectureHall;
    private String subject;
    private Group group;
    private Teacher teacher;
    private MonthTimetable monthTimetable;

    public DayTimetable() {
    }

    public DayTimetable(int id, LocalTime startTime, String lectureHall, String subject, Group group, Teacher teacher, MonthTimetable monthTimetable) {
        this.id = id;
        this.startTime = startTime;
        this.lectureHall = lectureHall;
        this.subject = subject;
        this.group = group;
        this.teacher = teacher;
        this.monthTimetable = monthTimetable;
    }

    public DayTimetable(LocalTime startTime, String lectureHall, String subject, Group group, Teacher teacher) {
        this.startTime = startTime;
        this.lectureHall = lectureHall;
        this.subject = subject;
        this.group = group;
        this.teacher = teacher;
    }

    public DayTimetable(LocalTime startTime, String lectureHall, String subject, Group group, Teacher teacher, MonthTimetable monthTimetable) {
        this.startTime = startTime;
        this.lectureHall = lectureHall;
        this.subject = subject;
        this.group = group;
        this.teacher = teacher;
        this.monthTimetable = monthTimetable;
    }

    public DayTimetable(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getLectureHall() {
        return lectureHall;
    }

    public void setLectureHall(String lectureHall) {
        this.lectureHall = lectureHall;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public MonthTimetable getMonthTimetable() {
        return monthTimetable;
    }

    public void setMonthTimetable(MonthTimetable monthTimetable) {
        this.monthTimetable = monthTimetable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTimetable that = (DayTimetable) o;
        return id == that.id && Objects.equals(startTime, that.startTime) && Objects.equals(lectureHall, that.lectureHall) && Objects.equals(subject, that.subject) && Objects.equals(group, that.group) && Objects.equals(teacher, that.teacher) && Objects.equals(monthTimetable, that.monthTimetable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, lectureHall, subject, group, teacher, monthTimetable);
    }

    @Override
    public String toString() {
        return "DayTimetable{" +
            "id=" + id +
            ", startTime=" + startTime +
            ", lectureHall='" + lectureHall + '\'' +
            ", subject='" + subject + '\'' +
            ", group=" + group +
            ", teacher=" + teacher +
            ", monthTimetable=" + monthTimetable +
            '}';
    }
}
