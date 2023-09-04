package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "home_phone_number", length = 8)
    private String homePhoneNumber;

    @Column(name = "work_phone_number", length = 8)
    private String workPhoneNumber;

    @Column(name = "mobile_phone_number", length = 8, nullable = false, unique = true)
    private String mobilePhoneNumber;

    @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE, orphanRemoval = true)
    @ToString.Exclude
    private Set<PersonHobby> hobbies = new HashSet<>();

    @ManyToOne
    private Address address;

    public Person(String firstName, String lastName, String email, LocalDate birthDate, String homePhoneNumber, String workPhoneNumber, String mobilePhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.homePhoneNumber = homePhoneNumber;
        this.workPhoneNumber = workPhoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void addHobby(Hobby hobby, Integer rating) {
        if (hobby != null) {
            PersonHobby personHobby = new PersonHobby(this, hobby, rating);
            this.hobbies.add(personHobby);
            hobby.getPersons().add(personHobby);
        }
    }
}