package ua.com.foxminded.university.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
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

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "cathedra_id")
    private Cathedra cathedra;

    public Teacher(int id, String firstName, String lastName, String phone, String qualification) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.qualification = qualification;
    }
}