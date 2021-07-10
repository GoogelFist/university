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
@Table(name = "cathedras")
public class Cathedra {
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
    @OneToMany(mappedBy = "cathedra")
    @JsonIgnore
    private List<Group> groups;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "cathedra")
    @JsonIgnore
    private List<Teacher> teachers;
}