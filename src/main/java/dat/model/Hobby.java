package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Hobby {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "wikiLink", nullable = false)
    private String wikiLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private HobbyCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private HobbyType type;

    @OneToMany(mappedBy = "hobby", cascade = CascadeType.MERGE, orphanRemoval = true)
    @ToString.Exclude
    private final Set<PersonHobby> persons = new HashSet<>();
}