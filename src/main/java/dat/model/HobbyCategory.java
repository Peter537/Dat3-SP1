package dat.model;

import lombok.Getter;

@Getter
public enum HobbyCategory {
    GENEREL("Generel"),
    EDUCATIONAL("Educational hobbies"),
    COLLECTION_HOBBY("Samler hobbyer"),
    COMPETITION("Konkurrence"),
    OBSERVATION("Observation");

    private final String name;

    HobbyCategory(String name) {
        this.name = name;
    }
}