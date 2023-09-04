package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
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


    public Address(String street, String state, String country, Zip zip) {
        this.street = street;
        this.state = state;
        this.country = country;
        this.zip = zip;
    }
}
