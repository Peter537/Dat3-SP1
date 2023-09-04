package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private Person createdBy;

    @ManyToMany
    private Set<Person> attendees;

    @ManyToOne
    private Hobby hobby;

    @ManyToOne
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "event_date")
    private LocalDate eventDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    public Event(Person createdBy, Hobby hobby, Address address, String description, Double price, LocalDate eventDate) {
        this.createdBy = createdBy;
        this.hobby = hobby;
        this.address = address;
        this.description = description;
        this.price = price;
        this.eventDate = eventDate;
    }

    @PrePersist
    public void prePersist() {
        lastUpdated = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = LocalDate.now();
    }
}