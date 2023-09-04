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
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Column(name = "home_phone_number", length = 8)
    private String homePhoneNumber;

    @Column(name = "work_phone_number", length = 8)
    private String workPhoneNumber;

    @Column(name = "mobile_phone_number", length = 8, unique = true)
    private String mobilePhoneNumber;

    @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Hobby> hobbies = new HashSet<>();

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

    public void addHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.add(hobby);
            hobby.getPersons().add(this);
        }
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}