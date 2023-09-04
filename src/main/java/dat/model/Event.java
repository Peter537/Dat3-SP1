package dat.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Person createdBy;

    @ManyToMany
    private Set<Person> attendees;

    @ManyToOne
    private Hobby hobby;

    @ManyToOne
    private Address address;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;

    @Temporal(TemporalType.DATE)
    private LocalDate eventDate;
}