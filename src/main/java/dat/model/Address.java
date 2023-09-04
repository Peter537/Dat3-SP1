package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "street")
    private String street;
    private String city;
    private String state;
    private String country;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Zip zip;

    @OneToMany(mappedBy = "address", cascade = CascadeType.MERGE)
    private Set<Person> person = new HashSet<>();


    public Address(String street, String state, String country, Zip zip) {
        this.street = street;
        this.state = state;
        this.country = country;
        this.zip = zip;
    }

}
