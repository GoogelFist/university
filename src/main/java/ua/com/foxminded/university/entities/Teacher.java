package ua.com.foxminded.university.entities;

import java.util.Objects;

public class Teacher {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String qualification;

    public Teacher(int id, String firstName, String lastName, String phone, String qualification) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.qualification = qualification;
    }

    public Teacher() {
    }

    public Teacher(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName) && Objects.equals(phone, teacher.phone) && Objects.equals(qualification, teacher.qualification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phone, qualification);
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", phone='" + phone + '\'' +
            ", qualification='" + qualification + '\'' +
            '}';
    }
}