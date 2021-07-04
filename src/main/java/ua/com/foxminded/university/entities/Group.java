package ua.com.foxminded.university.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "groups")
public class Group {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Student> students;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "cathedra_id")
    private Cathedra cathedra;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
}