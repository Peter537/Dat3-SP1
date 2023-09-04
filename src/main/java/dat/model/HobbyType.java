package dat.model;

import lombok.Getter;

@Getter
public enum HobbyType {
    INDOOR("Indendørs"),
    COMPETITION("Konkurrence"),
    OBSERVATION("Observation"),
    OUTDOOR("Udendørs"),
    COLLECTION_HOBBY("Samler hobbyer"),
    EDUCATIONAL("Educational hobbies"),
    UNDEFINED("---"),
    INDOOR_OUTDOOR("Indendørs/udendørs");

    private final String name;

    HobbyType(String name) {
        this.name = name;
    }
}