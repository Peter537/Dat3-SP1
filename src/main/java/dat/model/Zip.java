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
    private Integer zip;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "municipality_name")
    private String municipalityName;

    @OneToMany(mappedBy = "zip")
    @ToString.Exclude
    private Set<Address> address = new HashSet<>();
}