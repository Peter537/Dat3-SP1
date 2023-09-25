package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonHobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Hobby hobby;

    private Integer rating;

    public PersonHobby(Person person, Hobby hobby, Integer rating) {
        this.person = person;
        this.hobby = hobby;
        this.rating = rating;
    }

}