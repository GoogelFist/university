package ua.com.foxminded.university.entities;

import java.util.List;
import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private List<Student> students;
    private Cathedra cathedra;

    public Group(int id, String name, List<Student> students, Cathedra cathedra) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.cathedra = cathedra;
    }

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(int id, String name, Cathedra cathedra) {
        this.id = id;
        this.name = name;
        this.cathedra = cathedra;
    }

    public Group(String name, Cathedra cathedra) {
        this.name = name;
        this.cathedra = cathedra;
    }

    public Group(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name) && Objects.equals(students, group.students) && Objects.equals(cathedra, group.cathedra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, cathedra);
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", students=" + students +
            ", cathedra=" + cathedra +
            '}';
    }
}