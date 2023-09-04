package dat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Zip {

    @Id
    @Column(name = "zip")
    private int zip;
    @Column(name = "city_name")
    private String city_name;
    @Column(name = "region_name")
    private String region_name;
    @Column(name = "municipality_name")
    private String municipality_name;

    @OneToMany(mappedBy = "zip")
    private Set<Address> address = new HashSet<>();


}
