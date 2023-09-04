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
    @Column(name = "street", nullable = false)
    private String street;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Zip zip;

    @OneToMany(mappedBy = "address", cascade = CascadeType.MERGE, orphanRemoval = true)
    @ToString.Exclude
    private final Set<Person> person = new HashSet<>();

    public Address(String street, Zip zip) {
        this.street = street;
        this.zip = zip;
    }
}