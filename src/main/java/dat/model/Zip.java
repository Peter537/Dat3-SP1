package dat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @Column(name = "state_name")
    private String region_name;
    @Column(name = "municipality_name")
    private String municipality_name;



}
