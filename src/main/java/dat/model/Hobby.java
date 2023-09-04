package dat.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
public class Hobby {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "wikiLink", nullable = false)
    private String wikiLink;

    @Enumerated(EnumType.STRING)
    //    @Column(name = "category", nullable = false, columnDefinition = "VARCHAR(60) CHECK (category IN ('Generel', 'Educational hobbies', 'Samler hobbyer', 'Konkurrence', 'Observation'))")
//    @Column(columnDefinition = "VARCHAR(60) CHECK (category IN ('Generel', 'Educational hobbies', 'Samler hobbyer', 'Konkurrence', 'Observation'))")
    @Column(name = "category", nullable = false)
    private HobbyCategory category;

    @Enumerated(EnumType.STRING)
//    @Column(name = "type", nullable = false, columnDefinition = "VARCHAR(60) CHECK (type IN ('Indendørs', 'Konkurrence', 'Observation', 'Udendørs', 'Samler hobbyer', 'Educational hobbies', '---', 'Indendørs/udendørs'))")
    @Column(name = "type", nullable = false)
//    @Column(columnDefinition = "VARCHAR(60) CHECK (type IN ('Indendørs', 'Konkurrence', 'Observation', 'Udendørs', 'Samler hobbyer', 'Educational hobbies', '---', 'Indendørs/udendørs'))")
    private HobbyType type;

    @OneToMany(mappedBy = "hobby", cascade = CascadeType.MERGE, orphanRemoval = true)
    @ToString.Exclude
    private final Set<PersonHobby> persons = new HashSet<>();

    public Hobby(String name, String wikiLink, HobbyCategory category, HobbyType type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }
}