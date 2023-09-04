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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "wiki_link")
    private String wikiLink;

    @Enumerated(EnumType.STRING)
    private HobbyCategory category;

    @Enumerated(EnumType.STRING)
    private HobbyType type;

    @ManyToMany(mappedBy = "hobbies", fetch = FetchType.EAGER)
    @ToString.Exclude
    private final Set<Person> persons = new HashSet<>();
}