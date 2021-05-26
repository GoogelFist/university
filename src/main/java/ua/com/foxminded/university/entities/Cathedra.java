package ua.com.foxminded.university.entities;

import java.util.List;
import java.util.Objects;

public class Cathedra {
    private int id;
    private String name;
    private List<Group> groups;
    private List<Teacher> teachers;

    public Cathedra(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Cathedra() {
    }

    public Cathedra(int id) {
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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cathedra cathedra = (Cathedra) o;
        return id == cathedra.id && Objects.equals(name, cathedra.name) && Objects.equals(groups, cathedra.groups) && Objects.equals(teachers, cathedra.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, groups, teachers);
    }

    @Override
    public String toString() {
        return "Cathedra{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", groups=" + groups +
            ", teachers=" + teachers +
            '}';
    }
}