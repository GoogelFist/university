package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "qualification")
    private String qualification;

    @ManyToOne(cascade = {CascadeType.PERSIST,
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.MERGE})
    @JoinColumn(name = "cathedra_id")
    private Cathedra cathedra;

    public Teacher(int id, String firstName, String lastName, String phone, String qualification) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
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
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", phone='" + phone + '\'' +
            ", qualification='" + qualification + '\'' +
            '}';
    }
}