package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    private String email;
    private int age;

    @Column(name = "home_phone_number")
    private String homePhoneNumber;

    @Column(name = "work_phone_number")
    private String workPhoneNumber;

    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Hobby> hobbies;

    //@ManyToOne
    //private Address address;

    public Person(String firstName, String lastName, String email, int age, String homePhoneNumber, String workPhoneNumber, String mobilePhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.homePhoneNumber = homePhoneNumber;
        this.workPhoneNumber = workPhoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
        if (hobby != null) {
            hobby.getPersons().add(this);
        }
    }
}