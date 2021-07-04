package ua.com.foxminded.university.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "students")
public class Student {
    private static final String FIELD_CAN_NOT_BE_EMPTY = "This field cannot be empty";
    private static final String PHONE_REGEXP = "^((\\+7|7|8)+([0-9]){10})$";
    private static final String ERROR_REGEXP_MESSAGE = "Incorrect phone number format. Example +79001231212";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = FIELD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = PHONE_REGEXP, message = ERROR_REGEXP_MESSAGE)
    @Column(name = "phone")
    private String phone;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Student(int id, String firstName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}